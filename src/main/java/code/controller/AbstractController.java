package code.controller;

import code.view.Vues.Vue;

public abstract class AbstractController {
	protected Vue m_vue;
	// private Model m_model;
	
	public AbstractController (Vue vue)
	{
		m_vue = vue;
	}
	public abstract void initController();
	public abstract void initView();
}
