import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.projectFeatures.githubConnection
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2022.04"

project {

    vcsRoot(HttpsGithubComDmitriyCODERHttpPongRefsHeadsMain)

    buildType(HttpPong)

    features {
        githubConnection {
            id = "PROJECT_EXT_2"
            displayName = "GitHub.com"
            clientId = "2abe25e37e94780b14dd"
            clientSecret = "credentialsJSON:a577f9de-057d-4e21-80c2-bd09289249bc"
        }
    }
}

object HttpPong : BuildType({
    name = "http-pong"

    vcs {
        root(HttpsGithubComDmitriyCODERHttpPongRefsHeadsMain)
    }

    steps {
        dockerCommand {
            commandType = build {
                source = file {
                    path = "Dockerfile"
                }
                namesAndTags = "%env.TEAMCITY_BUILDCONF_NAME%"
            }
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        swabra {
        }
    }
})

object HttpsGithubComDmitriyCODERHttpPongRefsHeadsMain : GitVcsRoot({
    name = "https://github.com/DmitriyCODER/http-pong#refs/heads/main"
    url = "https://github.com/DmitriyCODER/http-pong"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = password {
        userName = "DmitriyCODER"
        password = "credentialsJSON:4de86b50-6b26-4eed-b8fc-2eb53095f04c"
    }
    param("oauthProviderId", "PROJECT_EXT_2")
})
