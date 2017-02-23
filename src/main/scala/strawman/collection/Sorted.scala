package strawman.collection

import strawman.collection.mutable.Builder

import scala.Ordering

/** Base trait for sorted collections */
trait Sorted[A] extends SortedLike[A, Sorted[A]]

trait SortedLike[A, +Repr] {

  def ordering: Ordering[A]

  def range(from: A, until: A): Repr

}

/** Polymorphic transformation methods on sorted collections */
trait SortedPolyTransforms[A, +C[X] <: Sorted[X]]
  extends IterablePolyTransforms[A, Iterable] {

  def map[B](f: A => B)(implicit ordering: Ordering[B]): C[B]

}

/**
  * Factories for collections whose elements require an ordering
  */
trait OrderingGuidedFactories[C[_]] {

  def builder[A](implicit ordering: Ordering[A]): Builder[A, C[A]]

  def empty[A : Ordering]: C[A] = builder[A].result

  def apply[A : Ordering](as: A*): C[A] = (builder[A] ++= as.toStrawman).result

}