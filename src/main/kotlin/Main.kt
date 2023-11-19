import kotlin.reflect.full.declaredMemberFunctions

interface TestRunner {
    fun runTest(steps: Any, test: () -> Unit)
}

class Runner : TestRunner {
    override fun runTest(steps: Any, test: () -> Unit) {
        val classFunctions = steps::class.declaredMemberFunctions

        classFunctions.filter { it.name.startsWith("before") }
            .forEach { println("Running ${it.name}...") }

        test.invoke()

        classFunctions.filter { it.name.startsWith("after") }
            .forEach { println("Running ${it.name}...") }
    }
}

class TestSteps {
    fun beforeTest() {
        println("Setting up before test...")
    }

    fun afterTest() {
        println("Cleaning up after test...")
    }
}

fun main() {
    val testRunner: TestRunner = Runner()
    val testSteps = TestSteps()

    testRunner.runTest(testSteps) {
        println("Running actual test...")
    }
}