def compile () {
    if (app_lang == "nodejs"){
        sh 'npm install'
    }
    if (app_lang == "maven"){
        sh 'mvn package'
    }
}
def Test Cases(){
    if (app_lang == "nodejs"){
        sh 'echo test'
    }
    if (app_lang == "maven"){
        sh 'echo test'
    }
}