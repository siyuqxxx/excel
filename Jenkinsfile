pipeline {
    agent any
    tools{
        maven 'maven-3.6.1'
        jdk 'JDK5'
    }
    stages {
        stage('checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], browser: [$class: 'GogsGit', repoUrl: 'http://10.168.1.106:43000/qiansiyu/excel'], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'rbzy-git-key', url: 'ssh://git@172.17.0.5/qiansiyu/excel.git']]])
            }
        }
        stage('maven package') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}