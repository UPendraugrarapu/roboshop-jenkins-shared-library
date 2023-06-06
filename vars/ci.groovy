def call () {
    if (!env.sonar_extra_opts){
        env.sonar_extra_opts=""
    }
    if (env.TAG_NAME ==~ ".*"){
        env.GTAG = "true"
    }else{
        env.GTAG = "false"
    }
    node ('workstation'){
        try {

            stage ('Check out Code'){
                cleanWs()
                git branch: 'main', url: 'https://github.com/upendraugrarapu/${component}'
            }

            sh 'env'
            print env.BRANCH_NAME

           if (env.BRANCH_NAME != "main") {
               stage('Compile / Build') {
                   common.compile()
               }
           }
           if (env.GTAG != "true" && env.BRANCH_NAME != "main"){
               stage('Test Cases') {
                   common.testcases()
               }
           }

            if (BRANCH_NAME ==~ "PR-.*"){
                stage('Code Quality') {
                    common.codequality()
                }
            }
            if (env.GTAG == "true" ){
                stage('Package') {
                    common.prepareArtifacts()
                }
                stage('Artifact Upload') {
                    common.testcases()
                }
            }
        }catch (e) {
            mail body: "<h1>${component}-Pipeline Failed \n ${BUILD_URL}</h1>", mimeType: 'text/html', from: 'ugrarapuupendra@gmail.com', subject: "${component}-Pipeline Failed", to: 'ugrarapuupendra@gmail.com'
        }

    }
}