/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package gradle.tooling.api.example

import org.gradle.tooling.GradleConnector
import org.gradle.tooling.IntermediateResultHandler
import org.jetbrains.plugins.gradle.model.ProjectImportAction
import kotlin.io.path.Path

fun main(args: Array<String>) {
    if (args.size == 0) {
        println("Specify a path of the repository running the Gradle daemon")
        System.exit(1)
    }
    val path = Path(args.get(0))
    val controller = GradleConnector.newConnector().forProjectDirectory(path.toFile()).connect().use { connection ->
        val buildAction = ProjectImportAction(false, false)
        buildAction.setParallelModelsFetch(true)
        connection.action().buildFinished(buildAction, IntermediateResultHandler<ProjectImportAction.AllModels>() {
            println("Parallel on: ${it.performanceTrace.toString()}")
        }).build().run()

        buildAction.setParallelModelsFetch(false)
        connection.action().buildFinished(buildAction, IntermediateResultHandler<ProjectImportAction.AllModels>() {
            println("Paralell off: ${it.performanceTrace.toString()}")
        }).build().run()
    }
}