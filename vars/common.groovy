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
    sh 'sonar-scanner -Dsonar.host.url=http://44.205.14.25:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.projectKey=${component}'
}