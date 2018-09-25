package hellogef.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.TextCellEditor;

import hellogef.model.NodeModel;

public class NodePart extends AbstractGraphicalEditPart implements PropertyChangeListener, NodeEditPart {

	protected DirectEditManager manager;
	
	//����ָ����������
	@Override
	public void performRequest(Request req) {
		//Ԥ�����ֱ�ӱ༭����
		if (req.getType().equals(RequestConstants.REQ_DIRECT_EDIT)) {
			if (manager == null) {
				NodeFigure figure = (NodeFigure) getFigure();
				manager = new NodeDirectEditManager(this, TextCellEditor.class, new NodeCellEditorLocator(figure));
			}
			manager.show();
		}
	}
	
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().equals(NodeModel.PROP_LOCATION)) {
			refreshVisuals();
		} else if (e.getPropertyName().equals(NodeModel.PROP_NAME)) {
			refreshVisuals();
		} else if (e.getPropertyName().equals(NodeModel.PROP_INPUTS)) {
			refreshTargetConnections();
		} else if (e.getPropertyName().equals(NodeModel.PROP_OUTPUTS)) {
			refreshSourceConnections();
		}

	}

	//������ͼFigure
	@Override
	protected IFigure createFigure() {
		return new NodeFigure();
	}

	//���������ɫ��Ӧ�ĸ���EditPolicy
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new NodeDirectEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new NodeEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new NodeGraphicalNodeEditPolicy());
	}
	
	//����EditPart
	@Override
	public void activate() {
		if (isActive())
			return;
		
		super.activate();
		((NodeModel)getModel()).addPropertyChangeListener(this);
	}
	
	//����EditPart
	@Override
	public void deactivate() {
		if (!isActive())
			return;
		
		super.deactivate();
		((NodeModel)getModel()).removePropertyChangeListener(this);
	}

}
