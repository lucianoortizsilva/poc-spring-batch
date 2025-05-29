package com.lucianoortizsilva.migration.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import com.lucianoortizsilva.migration.entrypoint.Payload;
import com.lucianoortizsilva.migration.entrypoint.RabbitMQProducer;

public class UISwing extends JFrame {
	
	private final RabbitMQProducer rabbitMQProducer = new RabbitMQProducer();
	private static final long serialVersionUID = 7168314514179629668L;
	private final GridBagConstraints gridBagConstraintsPrincipal = new GridBagConstraints();
	private final JPanel jPanelPrincipal = new JPanel(new GridBagLayout());
	private final JPanel jPanelButtonOrLoading = new JPanel(new GridBagLayout());
	private final JComboBox<JobName> jComboBoxDocTypes = new JComboBox<>();
	private final JButton jButtonValidate = new JButton("Send message");
	private final JLabel jLabelLoading = new JLabel();
	
	public UISwing() {
		initJFramePrincipal();
		initGridBagConstraintsPrincipal();
		loadDocTypes();
		addComboBox();
		addLine();
		addLine();
		addButtonAndLoadingGif();
		addLine();
		add(jPanelPrincipal);
		jButtonValidate.addActionListener(e -> send());
		setLocationRelativeTo(null);
	}
	
	private void initJFramePrincipal() {
		setTitle("Message RabbitMQ");
		setSize(300, 240);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
	}
	
	private void initGridBagConstraintsPrincipal() {
		gridBagConstraintsPrincipal.insets = new Insets(8, 8, 8, 8);
		gridBagConstraintsPrincipal.gridx = 0;
		gridBagConstraintsPrincipal.gridy = 0;
		gridBagConstraintsPrincipal.anchor = GridBagConstraints.WEST;
	}
	
	private void loadDocTypes() {
		try {
			jComboBoxDocTypes.setPreferredSize(new Dimension(200, 30));
			jComboBoxDocTypes.setName("jobName");
			final JobName flight = new JobName("flight");
			final JobName netflix = new JobName("netflix");
			final List<JobName> docTypes = List.of(flight, netflix);
			jComboBoxDocTypes.removeAllItems();
			for (final JobName job : docTypes) {
				jComboBoxDocTypes.addItem(job);
			}
		} catch (final Exception ex) {
			JOptionPane.showMessageDialog(this, "Error loading job names: " + ex.getMessage());
			disableButtonValidate();
		}
	}
	
	private void send() {
		final JobName selectedDocType = (JobName) jComboBoxDocTypes.getSelectedItem();
		disableAllFields();
		loading();
		sendMessageToRabbitMQ(selectedDocType.description());
	}
	
	private void sendMessageToRabbitMQ(final String jobName) {
		final SwingWorker<Void, Void> worker = new SwingWorker<>() {
			
			@Override
			protected Void doInBackground() throws Exception {
				final Payload payload = new Payload();
				payload.setName(jobName);
				rabbitMQProducer.send(payload);
				return null;
			}
			
			@Override
			protected void done() {
				try {
					get();
					showAutoCloseDialog(UISwing.this, "Info", "message sent", 3000);
				} catch (final Exception ex) {
					showAutoCloseDialog(UISwing.this, "Error", ex.getMessage(), 3000);
				} finally {
					enableFields();
					disableGifLoading();
				}
			}
		};
		worker.execute();
	}
	
	private void loading() {
		jLabelLoading.setVisible(true);
	}
	
	private void disableAllFields() {
		jComboBoxDocTypes.setEditable(false);
		jComboBoxDocTypes.setEnabled(false);
		jButtonValidate.setEnabled(false);
		jButtonValidate.setVisible(false);
	}
	
	private void enableFields() {
		jButtonValidate.setVisible(true);
		jButtonValidate.setEnabled(true);
		jComboBoxDocTypes.setEnabled(true);
		jComboBoxDocTypes.setEditable(false);
	}
	
	private void addLine() {
		gridBagConstraintsPrincipal.gridy++;
	}
	
	private void addComboBox() {
		jPanelPrincipal.add(new JLabel("Select Job Name:*"), gridBagConstraintsPrincipal);
		addLine();
		jPanelPrincipal.add(jComboBoxDocTypes, gridBagConstraintsPrincipal);
	}
	
	private void addButtonAndLoadingGif() {
		jPanelButtonOrLoading.removeAll();
		jPanelButtonOrLoading.setOpaque(false);
		final GridBagConstraints gridBagConstraintsInterno = new GridBagConstraints();
		initButtonCenterAndEnable(gridBagConstraintsInterno, gridBagConstraintsPrincipal);
		initLoadingCenterAndEnable(gridBagConstraintsInterno);
	}
	
	private void initLoadingCenterAndEnable(final GridBagConstraints gridBagConstraintsInterno) {
		jLabelLoading.setIcon(new ImageIcon(getClass().getResource("/rabbitmq.gif")));
		jLabelLoading.setVisible(false);
		gridBagConstraintsInterno.gridx = 0;
		gridBagConstraintsInterno.gridy = 0;
		gridBagConstraintsInterno.anchor = GridBagConstraints.CENTER;
		jPanelButtonOrLoading.add(jLabelLoading, gridBagConstraintsInterno);
		jLabelLoading.setVisible(false);
		jButtonValidate.setVisible(true);
		jPanelPrincipal.add(jPanelButtonOrLoading, gridBagConstraintsPrincipal);
		gridBagConstraintsPrincipal.gridwidth = 1;
		addLine();
	}
	
	private void initButtonCenterAndEnable(final GridBagConstraints gridBagConstraintsInterno, final GridBagConstraints gridBagConstraintsPrincipal) {
		gridBagConstraintsInterno.gridx = 0;
		gridBagConstraintsInterno.gridy = 0;
		gridBagConstraintsInterno.anchor = GridBagConstraints.CENTER;
		jPanelButtonOrLoading.add(jButtonValidate, gridBagConstraintsInterno);
		gridBagConstraintsPrincipal.gridx = 0;
		gridBagConstraintsPrincipal.gridwidth = 2;
		gridBagConstraintsPrincipal.anchor = GridBagConstraints.CENTER;
	}
	
	private void disableGifLoading() {
		jLabelLoading.setVisible(false);
	}
	
	private void disableButtonValidate() {
		jButtonValidate.setVisible(false);
		jButtonValidate.setEnabled(false);
	}
	
	public static void showAutoCloseDialog(final JFrame parent, final String type, final String message, final int durationMillis) {
		final JDialog dialog = new JDialog(parent, type, true);
		final JLabel label = new JLabel(message, SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(200, 50));
		dialog.getContentPane().add(label);
		dialog.pack();
		dialog.setLocationRelativeTo(parent);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				dialog.dispose();
			}
		}, durationMillis);
		new Thread(() -> dialog.setVisible(true)).start();
	}
	
	public record JobName(String description) {
		
		@Override
		public final String toString() {
			return description;
		}
	}
}