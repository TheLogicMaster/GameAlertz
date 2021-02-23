import com.google.gson.Gson;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("ConstantConditions")
public class Main extends JFrame {

	private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
	private final Gson gson = new Gson();
	private final GridBagConstraints c = new GridBagConstraints();
	private final JPanel contentPane;
	private Thread fetchThread;

	public Main () throws IOException, InterruptedException {
		super("Game Dealz");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.LINE_AXIS));
		contentPane = new JPanel();
		contentPane.setLayout(new GridBagLayout());
		c.weightx = 1;
		c.ipadx = 10;
		c.anchor = GridBagConstraints.WEST;
		JScrollPane scrollPane = new JScrollPane(contentPane);

		Store[] stores = sendRequest("stores", Store[].class);
		JComboBox<Store> storeSelect = new JComboBox<>(stores);
		controlPanel.add(storeSelect);

		JTextField titleField = new JTextField();
		titleField.setToolTipText("Game title");
		controlPanel.add(titleField);

		JComboBox<String> sortSelect = new JComboBox<>(new String[]{
			"Deal Rating", "Title", "Savings", "Price", "Metacritic", "Reviews", "Release", "recent"
		});
		controlPanel.add(sortSelect);

		JButton searchButton = new JButton("Search");
		controlPanel.add(searchButton);
		searchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked (MouseEvent event) {
				fetchDeals(titleField.getText(), ((Store)storeSelect.getSelectedItem()).getStoreID(), (String)sortSelect.getSelectedItem());
			}
		});

		add(controlPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);

		pack();
		setSize(800, 400);
		setVisible(true);

		fetchDeals("", "1", "Deal Rating");
	}

	private void fetchDeals(String title, String storeID, String sorting) {
		if (fetchThread != null) {
			fetchThread.interrupt();
			try {
				fetchThread.join();
			} catch (InterruptedException ignored) {
			}
		}
		fetchThread = new Thread(() -> {
			try {
				Deal[] deals = sendRequest(String.format(
					"deals?storeID=%s&sortBy=%s", storeID,
					URLEncoder.encode(sorting, StandardCharsets.UTF_8)
				) + (title.isEmpty() ? "" : "&title=" + URLEncoder.encode(title, StandardCharsets.UTF_8)), Deal[].class);
				contentPane.removeAll();
				for (int i = 0; i < deals.length; i++) {
					if (Thread.interrupted())
						return;
					c.gridy = i;
					c.gridx = 0;
					contentPane.add(new JLabel(new ImageIcon(ImageIO.read(new URL(deals[i].getThumb())))), c);
					c.gridx = 1;
					contentPane.add(new JLabel(deals[i].getTitle()), c);
					c.gridx = 2;
					contentPane.add(new JLabel((int)Float.parseFloat(deals[i].getSavings()) + "% off"), c);
					c.gridx = 3;
					contentPane.add(new JLabel(String.format(
						"<html><body><span style='text-decoration: line-through;'>$%s</span> $%s</body></html>", deals[i].getNormalPrice(), deals[i].getSalePrice())
					), c);
					JButton button = new JButton("See Deal");
					c.gridx = 4;
					contentPane.add(button, c);
					final String dealID = deals[i].getDealID();
					button.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked (MouseEvent e) {
							openURL("https://www.cheapshark.com/redirect?dealID=" + dealID);
						}
					});
					revalidate();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException ignored) {
			}
		});
		fetchThread.start();
	}

	private <T> T sendRequest(String address, Class<T> type) throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder(URI.create("https://www.cheapshark.com/api/1.0/" + address)).GET().build();
		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		return gson.fromJson(response.body(), type);
	}

	private void openURL(String url) {
		try {
			if (Desktop.isDesktopSupported())
				Desktop.getDesktop().browse(new URI(url));
			else
				Runtime.getRuntime().exec("/usr/bin/firefox -new-window " + url);
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void main (String[] args) throws IOException, InterruptedException {
		new Main();
	}
}
