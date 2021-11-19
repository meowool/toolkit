package com.meowool.sweekt

/**
 * Represents an information class.
 *
 * This annotation is similar with 'data class' behavior, but more powerful:
 * ```
 * // Prohibit the generation of 'copy' function,
 * //   and exclude properties in the primary constructor in the generated code.
 * @Info(generateCopy = false, joinPrimaryProperties = false)
 * class Decoder(val input: Input) {
 *   val name: String get() = input.readName()
 * }
 *
 * val decoder = Decoder(input)
 *
 * print(decoder.toString())    // "Decoder(name=John)"
 * decoder.copy()               // Error: Unresolved reference: 'copy'
 * ```
 *
 * @param generateCopy Generate `copy` method for the info class at compile time.
 * @param generateEquals Generate `equals` method for the info class at compile time.
 * @param generateHashCode Generate `hashCode` method for the info class at compile time.
 * @param generateToString Generate `toString` method for the info class at compile time.
 * @param generateComponentN Generate `componentN` method for the info class at compile time.
 * @param joinPrivateProperties Generated method contains the properties declared in the class body.
 * @param joinPrimaryProperties Generated method contains the properties declared in the primary constructor.
 * @param joinBodyProperties Generated method contains the properties declared in the class body.
 * @param callSuperEquals Generated 'equals' method contains a call to `super.equals(other)`.
 * @param callSuperHashCode Generated 'hashCode' method contains a call to `super.hashCode()`.
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class Info(
  val generateCopy: Boolean = true,
  val generateEquals: Boolean = true,
  val generateHashCode: Boolean = true,
  val generateToString: Boolean = true,
  val generateComponentN: Boolean = true,

  val joinPrivateProperties: Boolean = false,
  val joinPrimaryProperties: Boolean = true,
  val joinBodyProperties: Boolean = true,

  val callSuperEquals: Boolean = true,
  val callSuperHashCode: Boolean = true,
) {

  /**
   * Represents an invisible property of info, this property will not be included in the code generated by [Info].
   *
   * Note that this annotation depends on [Info]. Only properties declared in the class annotated with [Info] can use
   * this annotation, otherwise the Kotlin's compiler will report an error.
   *
   * For example:
   * ```
   * class User(val id: Int) {
   *   @Intangible(generateComponentN = true)
   *   val name: String get() = "user$id"
   * }
   *
   * print(User(1).toString())   // "User(id=1)"
   * print(User(1).component1()) // "1"
   * print(User(1).component2()) // "user1"
   * ```
   *
   * @param generateCopy Generate `copy` method for the info class at compile time.
   * @param generateEquals Generate `equals` method for the info class at compile time.
   * @param generateHashCode Generate `hashCode` method for the info class at compile time.
   * @param generateToString Generate `toString` method for the info class at compile time.
   * @param generateComponentN Generate `componentN` method for the info class at compile time.
   * @author 凛 (https://github.com/RinOrz)
   */
  @Target(AnnotationTarget.PROPERTY)
  @Retention(AnnotationRetention.SOURCE)
  annotation class Invisible(
    val generateCopy: Boolean = false,
    val generateEquals: Boolean = false,
    val generateHashCode: Boolean = false,
    val generateToString: Boolean = false,
    val generateComponentN: Boolean = false,
  )

  /**
   * Represents a synthetic interface for class marked with [Info], all classes marked with [Info] will automatically
   * implement this interface.
   *
   * @author 凛 (https://github.com/RinOrz)
   */
  @InternalSweektCompilerApi
  interface Synthetic {

    /**
     * Invokes the `equals` method generated by the compiler for the [Info] class.
     *
     * Note that this annotation depends on [Info]. This method can only be invoked in the class annotated with [Info],
     * otherwise the Kotlin's compiler will report an error.
     */
    @Suppress("UNUSED_PARAMETER")
    fun infoEquals(other: Any?): Boolean = compilerImplementation()

    /**
     * Invokes the `hashCode` method generated by the compiler for the [Info] class.
     *
     * Note that this annotation depends on [Info]. This method can only be invoked in the class annotated with [Info],
     * otherwise the Kotlin's compiler will report an error.
     */
    fun infoHashCode(): Int = compilerImplementation()

    /**
     * Invokes the `toString` method generated by the compiler for the [Info] class.
     *
     * Note that this annotation depends on [Info]. This method can only be invoked in the class annotated with [Info],
     * otherwise the Kotlin's compiler will report an error.
     */
    fun infoToString(): String = compilerImplementation()
  }
}