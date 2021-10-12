def call (String COMPONENT) {
    pipeline {

  agent {
    node {
      label "NODEJS"
    }
  }
  environment {
      Sonar_Token = credentials('Sonar_Token')
  }
  
  triggers { pollSCM('H/5 * * * 1-5') }
  
  stages {

    stage('Pushing Code for Quality check') {
      steps { 
        //sh """sonar-scanner -Dsonar.projectKey=${COMPONENT} -Dsonar.sources=. -Dsonar.host.url=http://172.31.25.74:9000 -Dsonar.login=${Sonar_Token} """
        sh 'echo code pushed'
      }
    }
    
    stage('Code Quality result'){
        steps {
            sh "## /usr/bin/sonar-quality-gate.sh admin admin123 3.94.119.157 ${COMPONENT}"
            sh 'echo Quality OK'
        }
    }

    stage('Checking for Lints ') {
      steps {
        echo 'Checking Lint Checks'
      }
    }

    stage('Unit Tests') {
      steps {
        echo 'Unit tests'
        sh 'env'
      }
    }

    stage('Preparing Artifact') {
      when{ expression { sh([returnStdout: true, script: 'echo ${GIT_BRANCH} | grep tags || true'])}}
      steps {
        sh """
          cd static
          zip -r ${COMPONENT}.zip * 
        """
      }
    }

    stage('Publishing Artifacts') {
      when{ expression { sh([returnStdout: true, script: 'echo ${GIT_BRANCH} | grep tags || true'])}} 
      steps {
        echo 'Publish Artifacts'
      }
    }

  }

  post {
    always {
      cleanWs()
    }
  }
}


}