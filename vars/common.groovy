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
//    sh 'echo ${TAG_NAME} >VERSION'
//
//    if (app_lang == "maven") {
//        sh 'zip -r ${component}-${TAG_NAME}.zip ${component}.jar schema VERSION'
//        //will get lot of files after maven packaging out of we need ${component}.jar file need to upload
//    } else{
//        sh 'zip -r ${component}-${TAG_NAME}.zip * -x Jenkinsfile'
//    }

   sh 'docker build -t 584455519448.dkr.ecr.us-east-1.amazonaws.com/${component}:${TAG_NAME} .'

//    the above one is ecr url
}

def artifactUpload(){
//    env.NEXUS_USER = sh ( script: 'aws ssm get-parameter --name prod.nexus.user --with-decryption | jq .Parameter.Value | xargs', returnStdout: true ).trim()
//    env.NEXUS_PASS = sh ( script: 'aws ssm get-parameter --name prod.nexus.pass --with-decryption | jq .Parameter.Value | xargs', returnStdout: true ).trim()
//    wrap([$class: 'MaskPasswordsBuildWrapper',
//          varPasswordPairs: [[password: NEXUS_PASS],[password: NEXUS_USER]]]) {
//        sh 'echo ${TAG_NAME} >VERSION'
//        sh 'curl -v -u ${NEXUS_USER}:${NEXUS_PASS} --upload-file ${component}-${TAG_NAME}.zip http://172.31.2.202:8081/repository/${component}/${component}-${TAG_NAME}.zip'
//    }
    //trim will delete the newline in output xargs removes the double quotes
    //we can access only env variables add env.for user and pass


//    ECR push commands will get from ECR it self
    sh 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 584455519448.dkr.ecr.us-east-1.amazonaws.com'
    sh 'docker push 584455519448.dkr.ecr.us-east-1.amazonaws.com/${component}:${TAG_NAME}'



}