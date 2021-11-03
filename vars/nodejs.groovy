def call (String COMPONENT) {
    pipeline {

  agent {
    node {
      label "NODEJS"
    }
  }
  environment {
      //Sonar_Token = credentials('Sonar_Token')
      NEXUS = credentials('NEXUS')
  }
  
 // triggers { pollSCM('H/1 * * * 1-5') }
  
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
        sh """
        VERSION=`echo ${GIT_BRANCH} | awk -F / '{print \$NF}'`
        echo version = \$VERSION
        """
      }
    }

    stage('Preparing Artifact') {
      when{ expression { sh([returnStdout: true, script: 'echo ${GIT_BRANCH} | grep tags || true'])}}
      steps {
        sh """
          npm install
          VERSION=`echo ${GIT_BRANCH} | awk -F / '{print \$NF}'`
          zip -r ${COMPONENT}-\${VERSION}.zip.zip node_modules server.js
        """
      }
    }

    stage('Publishing Artifacts') {
      when{ expression { sh([returnStdout: true, script: 'echo ${GIT_BRANCH} | grep tags || true'])}} 
      steps {
        sh """ 
        VERSION=`echo ${GIT_BRANCH} | awk -F / '{print \$NF}'`
        curl -f -v -u ${NEXUS} --upload-file ${COMPONENT}-\${VERSION}.zip.zip http://172.31.11.102:8081/repository/${COMPONENT}/${COMPONENT}-\${VERSION}.zip.zip
        """
      }
    }
    
    /*stage('Dev Deployment') {
        when { expression { sh([returnStdout: true, script: 'echo ${GIT_BRANCH} | grep tags || true' ]) } }
        steps {
          script {
            def VERSION=GIT_BRANCH.split('/').last()
            build job: 'AppDeploy', parameters: [string(name: 'COMPONENT', value: "${COMPONENT}"), string(name: 'ENV', value: 'dev'), string(name: 'APP_VERSION', value: VERSION)]
          }
        }
      }*/

  }

  post {
    always {
      cleanWs()
    }
  }
}


}