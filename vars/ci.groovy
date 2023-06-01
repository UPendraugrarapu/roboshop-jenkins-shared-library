def call () {
    if (!env.sonar_extra_opts){
        env.sonar_extra_opts=""
    }
    pipeline {
        agent any

        stages {

            stage('Compile/Build') {
                steps {
                        script{
                            withAWSParameterStore( credentialsId: 'PARAM',naming: 'absolute', path: 'sonarqube.user', recursive: false, regionName: 'eu-east-1') {
                                sh 'env'
                                sh 'exit 1'
                            }
                        common.compile()
                    }

                }
            }

            stage('Test Cases') {
                steps {
                    script {
                     common.testcases()
                    }

                }
            }
            stage('Code Quality') {
                steps {
                    script {
                        common.codequality()
                    }

                }
            }
        }

        post {
            failure{
                mail body: "<h1>${component}-Pipeline Failed \n ${BUILD_URL}</h1>", mimeType: 'text/html', from: 'ugrarapuupendra@gmail.com', subject: "${component}-Pipeline Failed", to: 'ugrarapuupendra@gmail.com'
            }
        }

    }
}