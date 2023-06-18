def call(){
    pipeline{
        agent any

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

//                        env.SSH_PASSWORD = sh ( script: 'aws ssm get-parameter --name prod.ssh.pass --with-decryption | jq .Parameter.Value | xargs', returnStdout: true ).trim()
//
//                        wrap([$class: 'MaskPasswordsBuildWrapper',
//                              varPasswordPairs: [[password: SSH_PASSWORD]]]) {
                            sh 'aws ec2 describe-instances --filters "Name=tag:Name, Values=${component}-${environment}" --query "Reservations[*].Instances[*].PrivateIpAddress" --output text >/tmp/servers'
//                            sh 'ansible-playbook -i /tmp/servers roboshop.yml -e role_name=${component} -e env=${environment} -e ansible_user=root -e ansible_password=${SSH_PASSWORD}'
                            sh'ansible-playbook -i /tmp/servers roboshop.yml -e role_name=${component} -e env=${environment}'

//                        For my code password doesnt required
                        }
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