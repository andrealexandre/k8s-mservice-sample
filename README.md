# K8s Service Sample

## Tool setup
* Install `brew install kubectl`
* Install `brew install minikube`
* Install `brew install sbt`

## Local K8s setup
* Install `minikube start`
    * If driver not setup `minikube start --vm-driver=hyperkit`
* Run to `kubectl get pods -A`, should be able to see some running pods in minikube.
* See some UI: `minikube dashboard`

## Sample microservice

This is a sample Akka HTTP endpoint keeping an in-memory database of users that can be created and listed.

Sources in the sample:
* `QuickstartApp.scala` -- contains the main method which bootstraps the application
* `UserRoutes.scala` -- Akka HTTP `routes` defining exposed endpoints
* `UserRegistry.scala` -- the actor which handles the registration requests
* `JsonFormats.scala` -- converts the JSON data from requests into Scala types and from Scala types into JSON responses

### Interacting with the sample

After starting the sample with `sbt run` the following requests can be made:

* List all users - `curl http://localhost:8080/users` 
* Create a user - `curl -XPOST http://localhost:8080/users -d '{"name": "Liselott", "age": 32, "countryOfResidence": "Norway"}' -H "Content-Type:application/json"`
* Get the details of one user - `curl http://localhost:8080/users/Liselott`
* Delete a user - `curl -XDELETE http://localhost:8080/users/Liselott`

## Package app as Docker image

* `sbt docker:publishLocal`
* `docker images -a`
* `docker run -p 8080:8080 k8s-mservice:0.1.0-SNAPSHOT`
* Run some curl commands to test app

## Deploy in minikube

* Before using any kubectl, we need to tell minikube to load the local image we just built
  * `minikube image load k8s-mservice:0.1.0-SNAPSHOT`
* Let's create a deployment
  * `kubectl apply -f k8s/k8s-mservice-deployment.yaml`
  * `kubectl get deployments`
  * `kubectl get pods`
* Try to curl the app
  * `kubectl port-forward deployments/k8s-mservice-deployment 8080:8080`
* Let's create a service
  * `kubectl apply -f k8s/k8s-mservice-service.yaml`
  * Expose service k8s service `minikube service k8s-mservice`
  * Tail logs in pod `kubectl logs -f pods/<pod_id>`
* Now try to change the deployment YAML and add an extra replica. Try to curl the service again.
* Creating an ingress
  * `minikube addons enable ingress` - addons for minikube
  * `kubectl apply -f k8s/k8s-mservice-ingress.yaml`
  * `kubectl get ingress` - Will list the IP and hostname for the ingress
  * Update `/etc/hosts` to allow your laptop to find the DNS `k8s-mservice.com` to the IP found previously

## Refs

* https://minikube.sigs.k8s.io/docs/start/
* https://github.com/akka/akka-http-quickstart-scala.g8
* https://www.scala-sbt.org/sbt-native-packager/formats/docker.html
* https://kubernetes.io/docs/concepts/workloads
* https://kubernetes.io/docs/concepts/services-networking