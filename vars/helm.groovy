def call(){
    pipeline{
        agent any

        options {
            ansiColor('xterm')
        }

        parameters {
            string(name: 'app_version', defaultValue: '', description: 'App Version')
            string(name: 'component', defaultValue: '', description: 'Component')
            string(name: 'environment', defaultValue: '', description: 'Environment')
        }

        stages{

          stage('Clone Application'){
            steps{
                dir('APP'){
                    git branch: 'main', url: "https://github.com/upendraugrarapu/${component}"
                }
                dir('HELM') {
                    git branch: 'main', url: "https://github.com/upendraugrarapu/roboshop-helm-chart"
                }
            }
          }
            stage('Deploy Helm chart'){

                steps{
                    script{
                        sh 'helm upgrade -i ${component} HELM/ -f APP/helm/${environment}.yaml --set appversion=${app_version}'
                    }


                }
            }
        }
        post{
            always{
                cleanWs()
            }
        }
    }
}