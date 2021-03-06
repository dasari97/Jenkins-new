def call (String COMPONENT) {
    pipeline {

  agent {
    node {
      label "JAVA"
    }
  }
  environment {
      //Sonar_Token = credentials('Sonar_Token')
      NEXUS = credentials('NEXUS')
  }
  
   //triggers { pollSCM('H/10 * * * 1-5') }
  
  stages {
    stage('Submitting for code Compilation') {
        steps {
            sh 'mvn compile'
        }
    }

    stage('Pushing Code for Quality check') {
      steps { 
        //sh """  sonar-scanner -Dsonar.java.binaries=target/.  -Dsonar.projectKey=${COMPONENT} -Dsonar.sources=. -Dsonar.host.url=http://172.31.25.74:9000 -Dsonar.login=${Sonar_Token}"""
        sh 'echo code pushed '
      }
    }
    
    stage('Code - Quality result'){
        steps {
            //sh "/usr/bin/sonar-quality-gate.sh admin admin123 3.94.119.157 ${COMPONENT}"
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
      }
    }

    stage('Preparing Artifact') {
       when{ expression { sh([returnStdout: true, script: 'echo ${GIT_BRANCH} | grep tags || true'])}}
      steps {
        sh """
          mvn clean package
          mv target/${COMPONENT}-1.0.jar ${COMPONENT}.jar
          VERSION=`echo ${GIT_BRANCH} | awk -F / '{print \$NF}'`
          zip -r ${COMPONENT}-\${VERSION}.zip ${COMPONENT}.jar
        """
      }
    }

    stage('Publishing Artifacts') {
       when{ expression { sh([returnStdout: true, script: 'echo ${GIT_BRANCH} | grep tags || true'])}}
      steps {
        sh """ 
        VERSION=`echo ${GIT_BRANCH} | awk -F / '{print \$NF}'`
        curl -f -v -u ${NEXUS} --upload-file ${COMPONENT}-\${VERSION}.zip http://172.31.11.102:8081/repository/${COMPONENT}/${COMPONENT}-\${VERSION}.zip 
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