package hellogef.view;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Rectangle;

public class NodeFigure extends Shape {

	private String name;
	private Label label;
	
	public NodeFigure() {
		this.label = new Label();
		this.add(label);
	}
	
	//先把背景色设为浅灰色，而后以背景色填充Figure区域内的圆角矩形
	@Override
	protected void fillShape(Graphics g) {
		g.pushState();
		Rectangle bound = this.getBounds().getCopy();
		g.setBackgroundColor(ColorConstants.lightGray);
		g.fillRoundRectangle(bound, 30, 30);
		g.popState();
	}

	//设置Figure区域宽高，而后以前景色绘制圆角矩形
	@Override
	protected void outlineShape(Graphics g) {
		Rectangle r = getBounds().getCopy();
		r.x = r.x + lineWidth / 2;
		r.y = r.y + lineWidth / 2;
		r.width = r.width - Math.max(1, lineWidth);
		r.height = r.height - Math.max(1, lineWidth);
		g.drawRoundRectangle(r, 30, 30);
	}
	
	public String getText() {
		return this.label.getText();
	}
	
	public Rectangle getTextBounds() {
		return this.label.getBounds();
	}
	
	public void setName(String name) {
		this.name = name;
		this.label.setText(name);
		this.repaint();
	}
	
	@Override
	public void setBounds(Rectangle rect) {
		super.setBounds(rect);
		this.label.setBounds(rect); //设置label标签控件的位置、大小
	}

}
