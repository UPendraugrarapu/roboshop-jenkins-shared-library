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

//            Get parameter from jenkins.io documentation
          stage('update the parameter store'){
            steps{
                sh 'aws ssm put-parameter --name ${environment}.${component}.app_version --type "String" --value "${app_version}" --overwrite'
            }
          }
            stage('Deploy servers'){

                steps{
                    script{
                        sh 'aws ec2 describe-instances --filters "Name=tag:Name, Values=${component}-${environment}" --query "Reservations[*].Instances[*].PrivateIpAddress" --output text | xargs -n1 >/tmp/servers'
                        sh 'ansible-playbook -i /tmp/servers roboshop.yml -e role_name=${component} -e env=${environment}'
//                        Here we are using ansible push mechanisim
//                        /tmp/servers is dynamic inventory file
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