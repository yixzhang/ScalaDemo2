package ch10_testing_dependency_injection

object SelfTypeAnnotations {
}

trait Graph{
  type Node <: BaseNode
  class BaseNode{
    this: Node =>

    def connectWith(n: Node): Edge = {
      new Edge(this, n)
    }
  }
  class Edge(from: Node, to: Node){
    def source() = from
    def target()  = to
  }
}

class LabeledGraph extends Graph{
  type Node = LabeledNode
  class LabeledNode(label: String) extends BaseNode{
    def getLabel = label
  }
}

