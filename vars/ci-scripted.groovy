def call () {
    if (!env.sonar_extra_opts){
        env.sonar_extra_opts=""
    }
    node ('workstation'){
        try {
            stage(Compile / Build) {
                sh 'env'
                common.compile()
            }
            stage('Test Cases') {
                common.testcases()
            }
            stage('Code Quality') {
                common.codequality()
            }
        }catch (e) {
            mail body: "<h1>${component}-Pipeline Failed \n ${BUILD_URL}</h1>", mimeType: 'text/html', from: 'ugrarapuupendra@gmail.com', subject: "${component}-Pipeline Failed", to: 'ugrarapuupendra@gmail.com'
        }

    }
}