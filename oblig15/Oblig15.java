package oblig15;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import twitter4j.*;
import twitter4j.Query.Unit;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class Oblig15 extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	public static final String CONSUMER_KEY = "6XGGICWjI7y9yogq5gr4UXZXZ";
	public static final String CONSUMER_KEY_SECRET = "AftuYCgXrZH8gTw1NkdZW5zxegUh1DQTkqXP9udbWSG8RmFHFn";
	public static final String ACCESS_TOKEN = "204958926-SPdlT8Fc9KDJ39tGcqkA6qDTBYEwq9D6hw5AK5fM";
	public static final String ACCESS_TOKEN_SECRET = "9hylTEwKUAZiUkl0Zi81SfUORu9biqkyAu71L4Y9kYGhG";

	private DefaultListModel<String> model = new DefaultListModel<String>();
	private JList<String> list = new JList<String>();
	private JTextField input = new JTextField();
	private JTextField classCodeInput = new JTextField("dat101");
	private JCheckBox geoLocationEnabled = new JCheckBox("GeoLocation query");
	private JPanel toolbar = new JPanel();

	private ConfigurationBuilder cb = new ConfigurationBuilder();
	private Configuration config;
	private Twitter twitter;

	public Oblig15() {
		super("Oblig15 - Twitter");

		setSize(770, 480);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		toolbar.setLayout(new FlowLayout());

		list.setModel(model);
		input.setPreferredSize(new Dimension(getWidth() / 3, 25));
		classCodeInput.setPreferredSize(new Dimension(60, 25));

		toolbar.add(new JLabel("Tweet:"));
		toolbar.add(input);
		toolbar.add(new JLabel("Fagkode: #"));
		toolbar.add(classCodeInput);

		addButton("Tweet", "tweet");
		addButton("Update feed", "update");

		toolbar.add(geoLocationEnabled);

		add(new JScrollPane(list), BorderLayout.CENTER);
		add(toolbar, BorderLayout.PAGE_END);

		setVisible(true);

		cb.setDebugEnabled(true).setOAuthConsumerKey(CONSUMER_KEY).setOAuthConsumerSecret(CONSUMER_KEY_SECRET).setOAuthAccessToken(ACCESS_TOKEN)
				.setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
		config = cb.build();

		twitter = new TwitterFactory(config).getInstance();
		initTwitterStream();
	}

	private void initTwitterStream() {
		TwitterStream twitterStream = new TwitterStreamFactory(config).getInstance();
		StatusListener statusListener = new StatusListener() {

			@Override
			public void onException(Exception arg0) {}

			@Override
			public void onTrackLimitationNotice(int arg0) {}

			@Override
			public void onStatus(Status status) {
				User user = status.getUser();
				model.insertElementAt(user.getName() + " [@" + user.getScreenName() + "] (" + user.getFollowersCount() + "): " + status.getText(), 0);
			}

			@Override
			public void onStallWarning(StallWarning arg0) {}

			@Override
			public void onScrubGeo(long arg0, long arg1) {}

			@Override
			public void onDeletionNotice(StatusDeletionNotice arg0) {}
		};

		twitterStream.addListener(statusListener);
		twitterStream.filter(new FilterQuery().track(new String[] { "#dat101" }));
	}

	private void addButton(String name, String id) {
		JButton button = new JButton(name);

		button.setName(id);
		button.addActionListener(this);

		toolbar.add(button);
	}

	private void updateTweets() {
		model.removeAllElements();
		Query query = new Query("#" + classCodeInput.getText());
		if (geoLocationEnabled.isSelected())
			query.setGeoCode(new GeoLocation(58.34050, 8.59343), 100.0, Unit.km);

		try {
			for (Status status : twitter.search(query).getTweets()) {
				User user = status.getUser();
				model.addElement(user.getName() + " [@" + user.getScreenName() + "] (" + user.getFollowersCount() + "): " + status.getText());
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String buttonID = ((JButton) e.getSource()).getName();

		switch (buttonID) {
			case "tweet":
				try {
					twitter.updateStatus(input.getText() + " #" + classCodeInput.getText());
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}
				input.setText("");
				break;
			case "update":
				updateTweets();
				break;
		}
	}

	public static void main(String[] args) {
		new Oblig15();
	}
}
