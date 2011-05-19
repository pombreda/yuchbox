package com.yuchting.yuchberry.client.weibo;

import com.yuchting.yuchberry.client.recvMain;

import local.localResource;
import net.rim.device.api.ui.Graphics;

public class WeiboUpdateField extends WeiboItemField{
	
	String m_sendUpdateText = "";
	
	public void AddDelControlField(boolean _add){
		AddDelEditTextArea(_add,m_sendUpdateText);
	}
	
	public int getPreferredHeight() {
		if(sm_extendWeiboItem == this){
			
			if(sm_editWeiboItem == this){
				return m_extendHeight + sm_editTextAreaHeight;
			}
			
			return m_extendHeight;
		}else{
			return sm_fontHeight;
		}
	}
	
	public void sublayout(int width, int height){
		
		if(sm_extendWeiboItem == this){
					
			setPositionChild(sm_editTextArea,0,0);
			layoutChild(sm_editTextArea,recvMain.fsm_display_width,sm_editTextAreaHeight);
			
			height = sm_editTextAreaHeight;			
						
		}else{		
			height = sm_fontHeight;
		}
		
		setExtent(recvMain.fsm_display_width,height);
	}
	
	public void subpaint(Graphics _g){
		
		if(sm_extendWeiboItem == this){
			
			paintChild(_g,sm_editTextArea);
			
		}else{
			
			if(sm_extendWeiboItem != null){
				_g.setColor(fsm_darkColor);
				_g.fillRect(0,0, recvMain.fsm_display_width,sm_closeHeight);
				_g.setColor(0);
			}
			
			
			int oldColour = _g.getColor();
	        try{
	        	_g.setColor(0x8f8f8f);            
	        	_g.drawText(recvMain.sm_local.getString(localResource.UPDATE_WEIBO_LABEL),2,2,Graphics.ELLIPSIS);
	        }finally{
	        	_g.setColor( oldColour );
	        }		
			
			if(sm_selectWeiboItem == this){
				_g.drawRoundRect(1,1,recvMain.fsm_display_width - 1,sm_fontHeight - 1,1,1);
			}else{
				_g.drawLine(0,sm_fontHeight - 1,recvMain.fsm_display_width,sm_fontHeight - 1);
			}
		}
	}
}