pipeline {
    agent any
    parameters {
        string(name: 'PROJECT', defaultValue: 'https://<token>@github.com/msudan21/SpringbootApp-Liberty.git', description: 'Enter Project name ')
        string(name: 'SERVICE_NAME', defaultValue: 'defaultService', description: 'Enter Service name ')

        }
    stages {
        stage('Checkout') {
            steps {
                 echo "Hello ${params.PROJECT}"
				 checkout([$class: 'GitSCM', branches: [[name: '*/master']], userRemoteConfigs: [[url: 'https://<token>@github.com/msudan21/SpringbootApp-Liberty.git']]])
	
            }
        }    
        stage('Build') { 
            steps {
                echo 'SpringBoot Application !!'
                sh 'mvn clean package' 
            }
        }

        stage('Dockerize') { 
            steps {
                echo 'Dockerizing application'
                sh 'docker build -t madhuapi/restapi:1.0 .'
				sh 'docker logout'
                sh 'docker login -u <userID> -p <Password> docker.io'
                sh 'docker tag madhuapi/restapi:1.0 msudan21/liberty:restapi'
				sh 'docker push msudan21/liberty:restapi'
				
            }
        }

         stage('Deploy') { 
            steps {
                echo 'Deploying application into OCP'
                sh "oc login --token=${params.OCTOKEN} --server=https://api.sandbox.x8i5.p1.openshiftapps.com:6443"
                
                sh '''
                    status=$?
                    cmd="oc delete deployment restapideployment"
                    if [ $cmd -eq 0 ]
                    then
                        echo "Success: Deployment Deleted"
                    else
                        echo "Failure: Deployment is not found "
                    fi

                    cmd="oc delete service restapideployment"
                    if [ $cmd -eq 0 ]
                    then
                        echo "Success: Service Deleted"
                    else
                        echo "Failure: Service is not found "
                    fi
                    
                    
                '''
                
                sh 'oc create configmap restapicm --from-file=src/main/resources/application.properties'
                timeout(time: 1, unit: 'MINUTES') {
                    
                }
                
                sh 'oc create -f deployment.yaml' 

                timeout(time: 1, unit: 'MINUTES') {
                    
                }

                sh 'oc create -f service.yaml' 

                timeout(time: 1, unit: 'MINUTES') {
                    
                }

                sh "oc expose service restapiservice"

                timeout(time: 1, unit: 'MINUTES') {
                    
                }
            }
        }

    }
}

