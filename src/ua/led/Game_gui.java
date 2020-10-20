package ua.led.two_d_game;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JToggleButton;

@SuppressWarnings("serial")
public class Game_gui implements KeyListener, ActionListener {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game_gui window = new Game_gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public Game_gui() {
		initialize();
	}

	public JFrame frame;
	private int frame_w = 600, frame_h = 350;
	private String GAME_NAME = "2D_GAME";

//	private ImageIcon background = new ImageIcon(getClass().getResource("/background.png"));
//	private BufferedImage background_bimg;
	private Point background_size = new Point(frame_w - 15, frame_h - 45);
	private ImagePanel drawPanel = new ImagePanel(new ImageIcon(getClass().getResource("/background.png")).getImage()
			.getScaledInstance(background_size.x, background_size.y, Image.SCALE_DEFAULT));

	private ImagePanel health_full = new ImagePanel(
			new ImageIcon(getClass().getResource("/health_full.png")).getImage());
	private ImagePanel health_2 = new ImagePanel(new ImageIcon(getClass().getResource("/health_2.png")).getImage());
	private ImagePanel health_1 = new ImagePanel(new ImageIcon(getClass().getResource("/health_1.png")).getImage());
	private ImagePanel health_0 = new ImagePanel(new ImageIcon(getClass().getResource("/health_0.png")).getImage());
	private Point health_size = new Point(32, 32);

	private ImagePanel pause_panel = new ImagePanel(new ImageIcon(getClass().getResource("/pause.png")).getImage());
	private ImagePanel pause_sign = new ImagePanel(
			new ImageIcon(getClass().getResource("/pause_sign1.png")).getImage());

	private ImagePanel game_over = new ImagePanel(new ImageIcon(getClass().getResource("/game_over.png")).getImage());
	private boolean game_over_bool = false;

	private ImagePanel level_1 = new ImagePanel(new ImageIcon(getClass().getResource("/level_1.png")).getImage()
			.getScaledInstance(20, 40, Image.SCALE_DEFAULT));
	private ImagePanel level_1_g = new ImagePanel(new ImageIcon(getClass().getResource("/level_1_g.png")).getImage()
			.getScaledInstance(20, 40, Image.SCALE_DEFAULT));
	private ImagePanel level_2 = new ImagePanel(new ImageIcon(getClass().getResource("/level_2.png")).getImage()
			.getScaledInstance(20, 40, Image.SCALE_DEFAULT));
	private ImagePanel level_2_g = new ImagePanel(new ImageIcon(getClass().getResource("/level_2_g.png")).getImage()
			.getScaledInstance(20, 40, Image.SCALE_DEFAULT));
	private ImagePanel level_3 = new ImagePanel(new ImageIcon(getClass().getResource("/level_3.png")).getImage()
			.getScaledInstance(20, 40, Image.SCALE_DEFAULT));
	private ImagePanel level_3_g = new ImagePanel(new ImageIcon(getClass().getResource("/level_3_g.png")).getImage()
			.getScaledInstance(20, 40, Image.SCALE_DEFAULT));

	private Point Pendalf_size = new Point(80, 100);
	private ImagePanel Pendalf_panel_r = new ImagePanel(new ImageIcon(getClass().getResource("/Pendalf.png")).getImage()
			.getScaledInstance(Pendalf_size.x, Pendalf_size.y, Image.SCALE_DEFAULT));
	private ImagePanel Pendalf_panel_l = new ImagePanel(new ImageIcon(getClass().getResource("/Pendalf1.png"))
			.getImage().getScaledInstance(Pendalf_size.x, Pendalf_size.y, Image.SCALE_DEFAULT));
//	private Image Pendalf_scaled = Pendalf.getImage().getScaledInstance(Pendalf_size.x, Pendalf_size.y, Image.SCALE_DEFAULT);
	public Point Pendalf_coords = new Point((frame_w - Pendalf_size.x) / 2, frame_h - Pendalf_size.y - 27 - 33);
	private String Side_aimed = "right";
	private boolean Pendalf_timer_started = false;
	private int Pendalf_health = 3;
	private final JLabel lblHealth = new JLabel("Health = " + Pendalf_health);

	private Point alien_size = new Point(90, 100);
	private ImagePanel alien_panel_r = new ImagePanel(new ImageIcon(getClass().getResource("/Minotaur_move.png"))
			.getImage().getScaledInstance(alien_size.x, alien_size.y, Image.SCALE_DEFAULT));
	private ImagePanel alien_panel_l = new ImagePanel(new ImageIcon(getClass().getResource("/Minotaur_move1.png"))
			.getImage().getScaledInstance(alien_size.x, alien_size.y, Image.SCALE_DEFAULT));
	private int alien_slide_r = 0;
	private int alien_slide_l = 0;

	private Point alien_start_coords_r = new Point(frame_w - 15, frame_h - alien_size.y - 2 * 27);
	private Point alien_start_coords_l = new Point(0 - alien_size.x, frame_h - alien_size.y - 2 * 27);
	private ArrayList<Alien> aliens = new ArrayList<Alien>();
//	private boolean alien_dead_r = true, alien_dead_l = true;

	private class Alien {
		public int alien_coords_x;
		public String alien_side;
		public int alien_type = 1;
		public double alien_speed = 1.0;

		public Alien(String side, int type, double speed) {
			alien_side = side;
			alien_type = type;
			alien_speed = speed;
			if (side.equals("right"))
				alien_coords_x = alien_start_coords_r.x;
			else
				alien_coords_x = alien_start_coords_l.x;
		}
	}

	private Point proj_size = new Point(38, 30);
	private ImagePanel proj_panel_r = new ImagePanel(new ImageIcon(getClass().getResource("/proj.png")).getImage()
			.getScaledInstance(proj_size.x, proj_size.y, Image.SCALE_DEFAULT));
	private ImagePanel proj_panel_l = new ImagePanel(new ImageIcon(getClass().getResource("/proj1.png")).getImage()
			.getScaledInstance(proj_size.x, proj_size.y, Image.SCALE_DEFAULT));

	private int proj_slide_r = 0;
	private int proj_slide_l = 0;

	private Point proj_start_coords_r = new Point(frame_w / 2 + 5 + Pendalf_size.x / 2, 300 - 60);
	private Point proj_start_coords_l = new Point(frame_w / 2 - 5 - Pendalf_size.x / 2 - proj_size.x, 300 - 60);
	private ArrayList<Proj> projectiles = new ArrayList<Proj>();
	private boolean proj_timer_started = false;

	public class Proj {
		public int proj_coords_x;
		public String proj_side;
		public double proj_speed = 1.0;

		public Proj(String side, double speed) {
			proj_side = side;
			proj_speed = speed;
			if (side.equals("right"))
				proj_coords_x = proj_start_coords_r.x;
			else
				proj_coords_x = proj_start_coords_l.x;
		}
	}

	private JToggleButton toggleBtnStartStop = new JToggleButton("Start (Ctrl)");
	private boolean pause_state = true;
//	private Icon i = new ImageIcon("button.png");

	private int level_num = 0;
	private JLabel lableLevelProgress = new JLabel("level " + (level_num + 1) + " progress  bar");
	private JProgressBar levelProgressBar = new JProgressBar();
	private int[] levels_max = { 10, 20, 30, 40, 50 };
	private long level_delay = 5500L;
	private double level_speed = 1.2;
	private int count_killed = 0;

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame(GAME_NAME);

		frame.setBounds(500, 200, frame_w, frame_h);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));

		drawPanel.setBounds(0, 0, frame_w - 5, frame_h - 31);
		drawPanel.setLayout(null);

		JPanel otherPanel = new JPanel(new BorderLayout()) {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);

				// Apply our own painting effect
				Graphics2D g2d = (Graphics2D) g.create();
				// 50% transparent Alpha
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

				g2d.setColor(getBackground());
				g2d.fillRect(0, 0, getWidth(), getHeight());

				g2d.dispose();
			}
		};
		otherPanel.setOpaque(false);
		frame.getContentPane().add(otherPanel);

		otherPanel.setBackground(new Color(0, 0, 250, 255));

		// Pause btn

		toggleBtnStartStop.setBounds(17, 13, 137, 25);
		frame.getContentPane().add(toggleBtnStartStop);
//		toggleBtnStartStop.setIcon(i);
		toggleBtnStartStop.setVisible(true);
		toggleBtnStartStop.setEnabled(false);

		game_over.setVisible(false);
		game_over.setBounds((600 - 120) / 2, (350 - 131 - 45) / 2, 120, 131);
		frame.getContentPane().add(game_over);

		pause_panel.setVisible(true);
		pause_panel.setBounds(0, 0, background_size.x, background_size.y);
		frame.getContentPane().add(pause_panel);

		pause_sign.setVisible(false);
		pause_sign.setBounds((600 - 100) / 2, (350 - 100 - 45) / 2, 100, 100);
		frame.getContentPane().add(pause_sign);

		// Progress info
		lableLevelProgress.setBounds(frame_w - 171, 5, 145, 16);
		lableLevelProgress.setForeground(Color.WHITE);
		frame.getContentPane().add(lableLevelProgress);
		lableLevelProgress.setVisible(true);

		levelProgressBar.setBounds(frame_w - 184, 24, 146, 14);
		frame.getContentPane().add(levelProgressBar);
		levelProgressBar.setValue(0);
		levelProgressBar.setMinimum(0);
		levelProgressBar.setMaximum(levels_max[level_num]);
		levelProgressBar.setVisible(true);

		level_1.setBounds(430, 45, 20, 40);
		level_1.setVisible(false);
		level_1_g.setBounds(430, 45, 20, 40);
		level_1_g.setVisible(false);
		level_2.setBounds(455, 45, 20, 40);
		level_2.setVisible(false);
		level_2_g.setBounds(455, 45, 20, 40);
		level_2_g.setVisible(false);
		level_3.setBounds(500, 45, 20, 40);
		level_3.setVisible(false);
		level_3_g.setBounds(500, 45, 20, 40);
		level_3_g.setVisible(false);
		frame.getContentPane().add(level_1);
		frame.getContentPane().add(level_1_g);
		frame.getContentPane().add(level_2);
		frame.getContentPane().add(level_2_g);
		frame.getContentPane().add(level_3);
		frame.getContentPane().add(level_3_g);

		// Pendalf
		Pendalf_panel_r.setBounds(Pendalf_coords.x, Pendalf_coords.y, Pendalf_size.x, Pendalf_size.y);
		Pendalf_panel_l.setBounds(Pendalf_coords.x, Pendalf_coords.y, Pendalf_size.x, Pendalf_size.y);
		Pendalf_panel_l.setVisible(false);
		drawPanel.add(Pendalf_panel_r);
		drawPanel.add(Pendalf_panel_l);

		lblHealth.setForeground(Color.WHITE);
		lblHealth.setBounds(270, 24, 90, 16);

		health_0.setBounds((frame_w - health_size.x) / 2, 24, health_size.x, health_size.y);
		health_1.setBounds((frame_w - health_size.x) / 2, 24, health_size.x, health_size.y);
		health_2.setBounds((frame_w - health_size.x) / 2, 24, health_size.x, health_size.y);
		health_full.setBounds((frame_w - health_size.x) / 2, 24, health_size.x, health_size.y);

		frame.getContentPane().add(health_full);
		frame.getContentPane().add(health_2);
		frame.getContentPane().add(health_1);
		frame.getContentPane().add(health_0);

		// Projectiles
		proj_panel_r.setBounds(proj_start_coords_r.x + proj_slide_r, proj_start_coords_r.y, proj_size.x, proj_size.y);
		proj_panel_l.setBounds(proj_start_coords_l.x - proj_slide_l, proj_start_coords_l.y, proj_size.x, proj_size.y);
		proj_panel_r.setVisible(false);
		proj_panel_l.setVisible(false);
		frame.getContentPane().add(proj_panel_r);
		frame.getContentPane().add(proj_panel_l);

		// Aliens
		alien_panel_r.setBounds(alien_start_coords_r.x - alien_slide_r, alien_start_coords_r.y, alien_size.x,
				alien_size.y);
		alien_panel_l.setBounds(alien_start_coords_l.x + alien_slide_l, alien_start_coords_l.y, alien_size.x,
				alien_size.y);
		alien_panel_r.setVisible(true);
		alien_panel_l.setVisible(true);
		frame.getContentPane().add(alien_panel_r);
		frame.getContentPane().add(alien_panel_l);

		frame.getContentPane().add(drawPanel);

		TimerTask repeatedTask = new TimerTask() {
			public void run() {
				movement();
			}
		};
		Timer gameMovement = new Timer("Timer");

		long delay = 100L;
		long period = 100L;
		gameMovement.scheduleAtFixedRate(repeatedTask, delay, period);

		TimerTask repeatedTask1 = new TimerTask() {
			public void run() {
				alienArival();
			}
		};
		Timer aliensArival = new Timer("Timer");

		period = level_delay;
		aliensArival.scheduleAtFixedRate(repeatedTask1, level_delay, period);

		frame.setFocusable(true);
		frame.addKeyListener(this);
		frame.setResizable(false);
		// TODO need to rework image constants
//		frame.setUndecorated(true);
//	    frame.setAlwaysOnTop(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void aimRight() {
		Side_aimed = "right";
		Pendalf_panel_l.setVisible(false);
		Pendalf_panel_r.setVisible(true);

	}

	public void aimLeft() {
		Side_aimed = "left";
		Pendalf_panel_r.setVisible(false);
		Pendalf_panel_l.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		frame.getContentPane().repaint();
		drawPanel.setFocusable(true);
	}

	// Повороты и пауза
	@Override
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_LEFT) {
			if (!pause_state) {
				if (!Pendalf_timer_started) {
					Pendalf_timer_started = true;

					TimerTask task = new TimerTask() {
						public void run() {
							Pendalf_timer_started = false;
						}
					};
					Timer rotateTimer = new Timer("Timer");
					long delay = 200L;
					rotateTimer.schedule(task, delay);

					if (key == KeyEvent.VK_RIGHT)
						aimRight();
					else
						aimLeft();
				}
			}
		}

//        if (key == KeyEvent.VK_UP) {
//            keyUp();
//        }

//        if (key == KeyEvent.VK_DOWN) {s
//            keyDown();
//        }

		// TODO Pause option

		if (key == KeyEvent.VK_CONTROL) {

			if (!pause_state) {
				pause_state = true;
				toggleBtnStartStop.setText("Resume (Ctrl)");
				toggleBtnStartStop.setSelected(false);
				pause_panel.setVisible(true);
				pause_sign.setVisible(true);
			} else {
				if (game_over_bool) {
					Pendalf_health = 3;
					health_0.setVisible(true);
					health_1.setVisible(true);
					health_2.setVisible(true);
					health_full.setVisible(true);
					game_over.setVisible(false);
					pause_panel.setVisible(false);
					level_num = 0;
					lableLevelProgress.setText("level " + (level_num + 1) + " progress bar");
					levelProgressBar.setValue(0);
					levelProgressBar.setMinimum(0);
					levelProgressBar.setMaximum(levels_max[level_num]);
					level_1.setVisible(false);
					level_1_g.setVisible(false);
					level_2.setVisible(false);
					level_2_g.setVisible(false);
					level_3.setVisible(false);
					level_3_g.setVisible(false);
				}
				pause_state = false;
				toggleBtnStartStop.setText("Pause (Ctrl)");
				toggleBtnStartStop.setSelected(true);
				pause_panel.setVisible(false);
				pause_sign.setVisible(false);
			}
		}

		frame.getContentPane().repaint();
	}

	// Стреляние
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_SPACE) {

			if (!pause_state) {
				if (!proj_timer_started) {

					if (Side_aimed.equals("right")) {
						proj_panel_r.setVisible(true);
						proj_slide_r = 0;
					} else {
						proj_panel_l.setVisible(true);
						proj_slide_l = 0;
					}

					double speed = 1.0;
					Proj temp = new Proj(Side_aimed, speed);
					projectiles.add(temp);

					TimerTask task = new TimerTask() {
						public void run() {
							proj_timer_started = false;
							proj_panel_r.setVisible(false);
							proj_panel_l.setVisible(false);

						}
					};
					Timer shootTimer = new Timer("Timer");

					long delay = 2000L;
					shootTimer.schedule(task, delay);
					proj_timer_started = true;

				}
			}
		}
		frame.getContentPane().repaint();
	}

	// Появление пришельцев
	private void alienArival() {
		if (!pause_state) {
			Random random = new Random();
			int rnd = random.nextInt(2);
			double drnd = random.nextDouble();
			if (4 * drnd > 4.0 || 3 * drnd < 2)
				drnd = level_speed;
			Alien alien = new Alien(rnd == 1 ? "left" : "right", 1, 4 * drnd);
			aliens.add(alien);
//			System.out.println("added new alien " + (rnd == 1 ? "left" : "right") + " " + aliens.size());
		}
		frame.getContentPane().repaint();
	}

	// Движение объектов от времени
	Proj p;
	Alien a;

	private void movement() {
		if (!pause_state) {

			// Moving projectile
			if (projectiles.size() >= 1) {
				for (int i = 0; i < projectiles.size(); i++) {
					p = projectiles.get(i);
					if (p.proj_side.equals("right")) {
						p.proj_coords_x += 20;
						proj_slide_r += 20;
						proj_panel_r.setBounds(proj_start_coords_r.x + proj_slide_r, proj_start_coords_r.y, proj_size.x,
								proj_size.y);
						if (p.proj_coords_x >= frame_w) {
							projectiles.remove(i);
							proj_slide_r = 0;
							p.proj_coords_x = proj_start_coords_r.x;
						}
					} else {
						p.proj_coords_x -= 20;
						proj_slide_l += 20;
						proj_panel_l.setBounds(proj_start_coords_l.x - proj_slide_l, proj_start_coords_l.y, proj_size.x,
								proj_size.y);
						if (p.proj_coords_x <= 0 - proj_size.x - 2) {
							projectiles.remove(i);
							proj_slide_l = 0;
							p.proj_coords_x = proj_start_coords_l.y;
						}
					}
				}
			}

			// Moving aliens
			if (aliens.size() >= 1) {
				for (int i = 0; i < aliens.size(); i++) {
					a = aliens.get(i);
					if (a.alien_side.equals("right")) {
						if (a.alien_coords_x <= (frame_w + Pendalf_size.x - 80) / 2) {
							Pendalf_health--;
							switch (Pendalf_health) {
							case 2:
								health_full.setVisible(false);
								break;
							case 1:
								health_2.setVisible(false);
								break;
							case 0:
								health_1.setVisible(false);
								pause_state = true;
								pause_panel.setVisible(true);
								toggleBtnStartStop.setText("Start (Ctrl)");
								game_over.setVisible(true);
								game_over_bool = true;
								toggleBtnStartStop.setSelected(false);
								break;
							}
							aliens.remove(i);
							alien_slide_r = 0;
							proj_slide_r = 0;
							alien_panel_r.setBounds(alien_start_coords_r.x - alien_slide_r, alien_start_coords_r.y,
									alien_size.x, alien_size.y);
						} else {
							double speed = a.alien_speed;
							a.alien_coords_x -= speed * 5;
							alien_slide_r += speed * 5;
							alien_panel_r.setBounds(alien_start_coords_r.x - alien_slide_r, alien_start_coords_r.y,
									alien_size.x, alien_size.y);
						}
					} else {

						if (a.alien_coords_x + alien_size.x >= (frame_w - Pendalf_size.x + 80) / 2) {
							Pendalf_health--;
							switch (Pendalf_health) {
							case 2:
								health_full.setVisible(false);
								break;
							case 1:
								health_2.setVisible(false);
								break;
							case 0:
								health_1.setVisible(false);
								pause_state = true;
								pause_panel.setVisible(true);
								toggleBtnStartStop.setText("Start (Ctrl)");
								game_over.setVisible(true);
								game_over_bool = true;
								toggleBtnStartStop.setSelected(false);
								break;
							}
							aliens.remove(i);
							alien_slide_l = 0;
							proj_slide_l = 0;
							alien_panel_l.setBounds(alien_start_coords_l.x - alien_slide_l, alien_start_coords_l.y,
									alien_size.x, alien_size.y);
						} else {
							double speed = a.alien_speed;
							a.alien_coords_x += speed * 5;
							alien_slide_l += speed * 5;
							alien_panel_l.setBounds(alien_start_coords_l.x + alien_slide_l, alien_start_coords_l.y,
									alien_size.x, alien_size.y);
						}
					}
				}
			}

			// Alien death
			if (projectiles.size() >= 1) {
				if (aliens.size() >= 1) {

					for (int i = 0; i < projectiles.size(); i++) {
						for (int j = 0; j < projectiles.size(); j++) {

							p = projectiles.get(i);
							a = aliens.get(j);

							if (p.proj_side.equals("right")) {
								if (a.alien_side.equals("right")) {

									if (p.proj_coords_x + proj_size.x >= a.alien_coords_x + alien_size.x / 2) {

										projectiles.remove(i);
										aliens.remove(j);
										alien_slide_r = 0;
										proj_slide_r = 0;
										TimerTask task = new TimerTask() {
											public void run() {
												alien_panel_r.setBounds(alien_start_coords_r.x - alien_slide_r,
														alien_start_coords_r.y, alien_size.x, alien_size.y);
												proj_panel_r.setBounds(proj_start_coords_r.x - proj_slide_r,
														proj_start_coords_r.y, proj_size.x, proj_size.y);
												proj_panel_r.setVisible(false);
											}
										};
										Timer despawnTimer = new Timer("Timer");
										long delay = 1000L;
										despawnTimer.schedule(task, delay);

//										proj_panel_r.setBounds(proj_start_coords_r.x - proj_slide_r, proj_start_coords_r.y, proj_size.x,
//												proj_size.y);
//										proj_panel_r.setVisible(false);

										count_killed++;
										levelProgressBar.setValue(count_killed);
										if (count_killed >= levelProgressBar.getMaximum()) {

											if (level_num + 1 <= 3) {
												level_num++;
												levelProgressBar.setMinimum(levels_max[level_num - 1]);
												levelProgressBar.setMaximum(levels_max[level_num]);
												level_delay -= 1000L;
												if (level_num == 1) {
													if (Pendalf_health <= 2) {
														level_1_g.setVisible(false);
														level_1.setVisible(true);
													} else {
														level_1_g.setVisible(true);
														level_1.setVisible(false);
													}
												} else if (level_num == 2) {
													if (Pendalf_health <= 2) {
														level_2_g.setVisible(false);
														level_2.setVisible(true);
													} else {
														level_2_g.setVisible(true);
														level_2.setVisible(false);
													}
												} else if (level_num == 3) {
													if (Pendalf_health <= 2) {
														level_3_g.setVisible(false);
														level_3.setVisible(true);
													} else {
														level_3_g.setVisible(true);
														level_3.setVisible(false);
													}
												}
											}

											lableLevelProgress.setText("level " + (level_num + 1) + " progress  bar");
										}
									}
								}
							} else {
								if (a.alien_side.equals("left")) {

									if (p.proj_coords_x <= a.alien_coords_x + alien_size.x / 2) {

										projectiles.remove(i);
										aliens.remove(j);
										alien_slide_l = 0;
										proj_slide_l = 0;

										TimerTask task = new TimerTask() {

											public void run() {
												alien_panel_l.setBounds(alien_start_coords_l.x + alien_slide_l,
														alien_start_coords_l.y, alien_size.x, alien_size.y);
												proj_panel_l.setBounds(proj_start_coords_l.x - proj_slide_l,
														proj_start_coords_l.y, proj_size.x, proj_size.y);
												proj_panel_l.setVisible(false);
											}
										};

										Timer despawnTimer = new Timer("Timer");
										long delay = 1000L;
										despawnTimer.schedule(task, delay);

//										proj_panel_l.setBounds(proj_start_coords_l.x - proj_slide_l, proj_start_coords_l.y, proj_size.x,
//												proj_size.y);
//										proj_panel_l.setVisible(false);

										count_killed++;
										levelProgressBar.setValue(count_killed);

										if (count_killed >= levelProgressBar.getMaximum()) {

											if (level_num + 1 <= 3) {
												level_num++;
												levelProgressBar.setMinimum(levels_max[level_num - 1]);
												levelProgressBar.setMaximum(levels_max[level_num]);
												level_delay -= 1000L;
												if (level_num == 1) {
													if (Pendalf_health <= 2) {
														level_1_g.setVisible(false);
														level_1.setVisible(true);
													} else {
														level_1_g.setVisible(true);
														level_1.setVisible(false);
													}
												} else if (level_num == 2) {
													if (Pendalf_health <= 2) {
														level_2_g.setVisible(false);
														level_2.setVisible(true);
													} else {
														level_2_g.setVisible(true);
														level_2.setVisible(false);
													}
												} else if (level_num == 3) {
													if (Pendalf_health <= 2) {
														level_3_g.setVisible(false);
														level_3.setVisible(true);
													} else {
														level_3_g.setVisible(true);
														level_3.setVisible(false);
													}
												}
											}

											lableLevelProgress.setText("level " + (level_num + 1) + " progress  bar");
										}
									}
								}
							}
						}
					}
				}
			}
		}

		frame.getContentPane().repaint();
	}

	class ImagePanel extends JPanel {

		private Image img;

		public ImagePanel(String img) {
			this(new ImageIcon(img).getImage());
		}

		public ImagePanel(Image img) {
			this.img = img;
			Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
			setPreferredSize(size);
			setMinimumSize(size);
			setMaximumSize(size);
			setSize(size);
			setLayout(null);
		}

		public void paintComponent(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}
}
