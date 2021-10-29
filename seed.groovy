folder('CI-Pipelines') {
  displayName('CI-Pipelines')
  description('CI-Pipelines')
}

def component = ["frontend", "catalogue", "cart", "shipping", "payment", "user"]

def count = (component.size() -1)

for(int i in 0..count) {

def j=component[i]
pipelineJob("CI-Pipelines/${j}") {
  configure { flowdefinition ->
    flowdefinition << delegate.'definition'(class:'org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition',plugin:'workflow-cps') {
      'scm'(class:'hudson.plugins.git.GitSCM',plugin:'git') {
        'userRemoteConfigs' {
          'hudson.plugins.git.UserRemoteConfig' {
            'url'("https://dasarisaikrishna97@dev.azure.com/dasarisaikrishna97/Roboshop/_git/${j}")
            'refspec'('\'+refs/tags/*\':\'refs/remotes/origin/tags/*\'')
          }
        }
        'branches' {
          'hudson.plugins.git.BranchSpec' {
              'name'('*/tags/*')
            }
          'hudson.plugins.git.BranchSpec' {
            'name'('*/main')
          }
        }
      }
      'scriptPath'('jenkinsfile')
      'lightweight'(true)
    }
  }
 }
}

folder('Mutable-Infra') {
  displayName('Mutable-Infra')
  description('Mutable-Infra')
}
pipelineJob("Mutable-Infra/VPC") {
  configure { flowdefinition ->
    flowdefinition << delegate.'definition'(class:'org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition',plugin:'workflow-cps') {
      'scm'(class:'hudson.plugins.git.GitSCM',plugin:'git') {
        'userRemoteConfigs' {
          'hudson.plugins.git.UserRemoteConfig' {
            'url'("https://dasarisaikrishna97@dev.azure.com/dasarisaikrishna97/Roboshop/_git/terraform-mutable")
            'refspec'('\'+refs/tags/*\':\'refs/remotes/origin/tags/*\'')
          }
        }
        'branches' {
          'hudson.plugins.git.BranchSpec' {
              'name'('*/tags/*')
            }
          'hudson.plugins.git.BranchSpec' {
            'name'('*/main')
          }
        }
      }
      'scriptPath'('VPC/jenkinsfile')
      'lightweight'(true)
    }
  }
 }
 
 folder('Mutable-Infra') {
  displayName('Mutable-Infra')
  description('Mutable-Infra')
}
pipelineJob("Mutable-Infra/DB") {
  configure { flowdefinition ->
    flowdefinition << delegate.'definition'(class:'org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition',plugin:'workflow-cps') {
      'scm'(class:'hudson.plugins.git.GitSCM',plugin:'git') {
        'userRemoteConfigs' {
          'hudson.plugins.git.UserRemoteConfig' {
            'url'("https://dasarisaikrishna97@dev.azure.com/dasarisaikrishna97/Roboshop/_git/terraform-mutable")
            'refspec'('\'+refs/tags/*\':\'refs/remotes/origin/tags/*\'')
          }
        }
        'branches' {
          'hudson.plugins.git.BranchSpec' {
              'name'('*/tags/*')
            }
          'hudson.plugins.git.BranchSpec' {
            'name'('*/main')
          }
        }
      }
      'scriptPath'('Databases/jenkinsfile')
      'lightweight'(true)
    }
  }
 }
 
 pipelineJob("Mutable-Infra/Destroy") {
  configure { flowdefinition ->
    flowdefinition << delegate.'definition'(class:'org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition',plugin:'workflow-cps') {
      'scm'(class:'hudson.plugins.git.GitSCM',plugin:'git') {
        'userRemoteConfigs' {
          'hudson.plugins.git.UserRemoteConfig' {
            'url'("https://dasarisaikrishna97@dev.azure.com/dasarisaikrishna97/Roboshop/_git/terraform-mutable")
            'refspec'('\'+refs/tags/*\':\'refs/remotes/origin/tags/*\'')
          }
        }
        'branches' {
          'hudson.plugins.git.BranchSpec' {
              'name'('*/tags/*')
            }
          'hudson.plugins.git.BranchSpec' {
            'name'('*/main')
          }
        }
      }
      'scriptPath'('Jenkinsfile-destroy')
      'lightweight'(true)
    }
  }
 }
 
 
 folder('Application_MI') {
  displayName('Application_MI')
  description('Application_MI')
}

def infra = ["frontend", "catalogue", "cart", "shipping", "payment", "user"]

def count1 = (infra.size() -1)

for(int i in 0..count1) {

def j=infra[i]
pipelineJob("Application_MI/${j}") {
  configure { flowdefinition ->
    flowdefinition << delegate.'definition'(class:'org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition',plugin:'workflow-cps') {
      'scm'(class:'hudson.plugins.git.GitSCM',plugin:'git') {
        'userRemoteConfigs' {
          'hudson.plugins.git.UserRemoteConfig' {
            'url'("https://dasarisaikrishna97@dev.azure.com/dasarisaikrishna97/Roboshop/_git/${j}")
            'refspec'('\'+refs/tags/*\':\'refs/remotes/origin/tags/*\'')
          }
        }
        'branches' {
          'hudson.plugins.git.BranchSpec' {
              'name'('*/tags/*')
            }
          'hudson.plugins.git.BranchSpec' {
            'name'('*/main')
          }
        }
      }
      'scriptPath'('jenkinsfile-infra')
      'lightweight'(true)
    }
  }
 }
}

folder('MI') {
  displayName('MI')
  description('MI')
}

def components = ["Databases", "VPC"]

def counts = (components.size() -1)

for(int i in 0..counts) {

def j=components[i]
pipelineJob("MI/${j}") {
  configure { flowdefinition ->
    flowdefinition << delegate.'definition'(class:'org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition',plugin:'workflow-cps') {
      'scm'(class:'hudson.plugins.git.GitSCM',plugin:'git') {
        'userRemoteConfigs' {
          'hudson.plugins.git.UserRemoteConfig' {
            'url'("https://dasarisaikrishna97@dev.azure.com/dasarisaikrishna97/Roboshop/_git/terraform-mutable")
            'refspec'('\'+refs/tags/*\':\'refs/remotes/origin/tags/*\'')
          }
        }
        'branches' {
          'hudson.plugins.git.BranchSpec' {
              'name'('*/tags/*')
            }
          'hudson.plugins.git.BranchSpec' {
            'name'('*/main')
          }
        }
      }
      'scriptPath'('VPC/jenkinsfile')
      'lightweight'(true)
    }
  }
 }
}