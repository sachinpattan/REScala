package examples.miscellanea

import react._
import macro.SignalMacro.{SignalM => Signal}
import swing.{Panel, MainFrame, SimpleSwingApplication}
import java.awt.{Graphics2D, Dimension}
import java.awt.Point
import scala.collection.mutable.ListBuffer


object SmashingParticles extends SimpleSwingApplication {
  
 
  val toDraw = ListBuffer[Function1[Graphics2D,Unit]]()
  type Delta = Point
  
  class Oval(center: Signal[Point], radius: Signal[Int]) {
    toDraw += ((g: Graphics2D) => 
      {g.fillOval(center.getVal.x,center.getVal.y, radius.getVal, radius.getVal)})
    
    override def toString = "Circle("+ center + "," + radius +")"
  }

  
  val base = Var(0)
  val simpleTime = Signal{base()}
  val time = Signal{simpleTime() % 200} // cyclic time :)
  
  
  val point1 = Signal{ new Point(20+time(), 20+time())}
  new Oval(point1, time)
  val point2 = Signal{ new Point(40+time(), 80+time())}
  new Oval(point2, time)
  val point3 = Signal{ new Point(80+time(), 40+time())}
  new Oval(point3, time)
  val point4 = Signal{ new Point(160+ time(), 160+time())}
  new Oval(point4, time)
  
 

  
  override def main(args: Array[String]){
    super.main(args)
    while (true) { {      
	  frame.repaint
        Thread sleep 20
        base()= base.getVal + 1
      }
    }
  }
 
  // drawing code
  def top = frame  
  val frame = new MainFrame {
    contents = new Panel() {
      preferredSize = new Dimension(600, 600)
      override def paintComponent(g: Graphics2D) {    
        toDraw.map(x => x(g)) 
      }
    }    
  }
  
}



