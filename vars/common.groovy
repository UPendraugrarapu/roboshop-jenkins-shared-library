def compile () {
    if (app_lang == "nodejs"){
        sh 'npm install'
    }
    if (app_lang == "maven"){
        sh 'mvn package'
    }
}
def testcases(){
    // npm test
    // maven test
    // python -m unittests
    // go test
    sh 'echo OK'
  }

def codequality() {
    withAWSParameterStore( credentialsId: 'PARAM1',naming: 'absolute', path: '/sonarqube', recursive: true, regionName: 'us-east-1') {
//        sh 'sonar-scanner -Dsonar.host.url=http://3.239.161.41:9000 -Dsonar.login=${SONARQUBE_USER} -Dsonar.password=${SONARQUBE_PASS} -Dsonar.projectKey=${component} ${sonar_extra_opts} -Dsonar.qualitygate.wait=true'
        sh 'echo ok'
    }

}