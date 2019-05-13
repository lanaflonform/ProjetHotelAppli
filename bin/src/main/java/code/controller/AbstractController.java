package model;

public abstract class AbstractController {
	private Vue m_vue;
	// private Model m_model;
	
	public AbstractController (Vue vue)
	{
		m_vue = vue;
	}
	public abstract void initController();
	public abstract initView();
}
