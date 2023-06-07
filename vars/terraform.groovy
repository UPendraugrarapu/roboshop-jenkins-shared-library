def call () {
    pipeline {
        agent any

        parameters {
            string(name : 'ENV', defaultValue : '', description: 'Which environment?')
            string(name : 'ACTION', defaultValue : '', description: 'Which environment?')
        }
        options {
            ansiColor('xterm')
        }
        stages {
            stage('init') {
                steps {
                    sh 'terraform init -backend-config=env-${ENV}/state.tfvars'
                }
            }
            stage ('Apply') {
                steps {
                    sh 'terraform ${ACTION} -auto-approve -var-file=env-dev/main.tfvars'
                    //sh 'echo'
                }
            }
        }
    }
}