package reduce

object Reduce {
  def sum(array: Array[Int]): Int = {
    array.reduce(_ + _)
  }
}
