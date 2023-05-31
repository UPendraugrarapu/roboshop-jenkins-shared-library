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