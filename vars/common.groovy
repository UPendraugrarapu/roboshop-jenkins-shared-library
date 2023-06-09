def compile () {
    if (app_lang == "nodejs"){
        sh 'npm install'
    }
    if (app_lang == "maven"){
        sh 'mvn package; mv target/${component}-1.0.jar ${component}.jar'
        //the second command from shipping component
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
def prepareArtifacts(){
    sh 'echo ${TAG_NAME} >VERSION'
    if (app_lang == "nodejs" || app_lang == "angular"){
        sh 'zip -r ${component}-${TAG_NAME}.zip * -x Jenkinsfile'
    }
    if (app_lang == "maven") {
        sh 'zip -r ${component}-${TAG_NAME}.zip ${component}.jar VERSION'
        //will get lot of files after maven packaging out of we need ${component}.jar file need to upload
    }
}

def artifactUpload(){
    sh 'echo ${TAG_NAME} >VERSION'
    //if (app_lang == "nodejs" || app_lang == "angular"){
        sh 'curl -v -u admin:admin123 --upload-file ${component}-${TAG_NAME}.zip http://172.31.2.202:8081/repository/${component}/${component}-${TAG_NAME}.zip'
   // }

}