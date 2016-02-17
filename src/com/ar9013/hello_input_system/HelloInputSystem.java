package com.ar9013.hello_input_system;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.InputListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.material.MaterialDef;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/** Sample 5 - how to map keys and mousebuttons to actions */
public class HelloInputSystem extends SimpleApplication{

	public static void main(String[] args) {
		HelloInputSystem app = new HelloInputSystem();
		app.start();

	}
	
	protected Geometry player;
	Boolean isRunning = true;

	@Override
	public void simpleInitApp() {
		 Box b = new Box(1, 1, 1);
		 player = new Geometry("Player", b);
		 Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		    mat.setColor("Color", ColorRGBA.Blue);
		    player.setMaterial(mat);
		 rootNode.attachChild(player);
		 initKeys();
		
	}

	/** Custom Keybinding: Map named actions to inputs. */
	private void initKeys(){
		// You can map one or several inputs to one named action
		inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
		inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_J));
		inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_K));
		inputManager.addMapping("Rotate", new KeyTrigger(keyInput.KEY_SPACE),new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		
		
		// Add the names to the action listener.
		inputManager.addListener(actionListener, "Pause");
		inputManager.addListener(analogListener,"Left", "Right", "Rotate");
	}
	
	private ActionListener actionListener = new ActionListener() {
		
		@Override
		public void onAction(String name, boolean keyPressed, float tpf) {
			
			if(name.equals("Pause") && !keyPressed){
				isRunning = !isRunning;
			}
		}
	};
	
	// 類比手把控制
	private AnalogListener analogListener = new AnalogListener() {
		
		@Override
		public void onAnalog(String name, float value, float tpf) {
			if(isRunning){
				if(name.equals("Rotate")){
					 player.rotate(0, value*speed, 0);
				}
				if(name.equals("Left")){
					 Vector3f v = player.getLocalTranslation(); // 取得位置
			          player.setLocalTranslation(v.x - value*speed, v.y, v.z); // 往x的負方向移動
				}
				 if (name.equals("Right")) {
			          Vector3f v = player.getLocalTranslation();
			          player.setLocalTranslation(v.x + value*speed, v.y, v.z);
			        }
				
				
			}else {
		        System.out.println("Press P to unpause.");
		      }
			
		}
	};
}
