package minilauncher.layout;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import minilauncher.core.App;

public class Layout extends JFrame {
	public Layout(Properties properties) {
		setTitle(properties.name);
		setSize(properties.width, properties.height);
		if (properties.menuBar != null) setJMenuBar(properties.menuBar);

		// Set the window icon
		try {
			BufferedImage logo = ImageIO.read(App.class.getResourceAsStream("/resources/logo.png"));
			setIconImage(logo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static class Properties {
		public String name = "MiniLauncher";
		public int width = 1000;
		public int height = 600;
		public JMenuBar menuBar = null;
	}
}
