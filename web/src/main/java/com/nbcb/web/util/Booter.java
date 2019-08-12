package com.nbcb.web.util;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.nbcb.core.game.Game;
import com.nbcb.core.room.Room;
import com.nbcb.core.server.Server;
import com.nbcb.core.user.Player;
import com.nbcb.majiang.action.MajiangAction;
import com.nbcb.majiang.card.MajiangAllCards;
import com.nbcb.majiang.card.MajiangCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.ningbo.NingboMajiangRoomInfo;

public class Booter {

	private static final Logger logger = LoggerFactory.getLogger(Booter.class);

	private MajiangGame game;

	private MajiangAllCards mac;

	public MajiangAllCards getMac() {
		return mac;
	}

	public void setMac(MajiangAllCards mac) {
		this.mac = mac;
	}

	public MajiangGame getGame() {
		return game;
	}

	public void setGame(MajiangGame game) {
		this.game = game;
	}

	public static MajiangAction parse(String content, Server server, String roomId) {
		Room room = server.getRoom(roomId);
		Game game = room.currentGame();
		// System.out.println(game == null);

		String[] str = content.split(" ");
		// System.out.println("str size " + str.length);
		if (str.length < 3) {
			return null;
		}
		String[] cards = new String[str.length - 2];
		System.arraycopy(str, 2, cards, 0, cards.length);
		int playerNum = Integer.parseInt(str[0]);
		int action = Integer.parseInt(str[1]);

		MajiangCards mcs = new MajiangCards();
		for (int i = 0; i < cards.length; i++) {
			mcs.addTailCard(game.getAllCards().findCardByNumber(Integer.parseInt(cards[i])));
		}

		Player player = room.getPlayerByOrder(Integer.parseInt(str[0]));

		return new MajiangAction(player, action, mcs, true);
	}

	private static NingboMajiangRoomInfo createRoomInfo() {
		NingboMajiangRoomInfo ri = new NingboMajiangRoomInfo();
		ri.setBaida(7);
		ri.setHunpengqing(true);
		ri.setName("ningboMajiang");
		ri.setPlayerNum(3);
		ri.setStartFan(4);
		ri.setTotalJu(2);
		return ri;
	}

	public static void main(String[] args) {

		String[] configs = new String[] { "E:\\maven\\game\\web\\src\\main\\resources\\spring\\majiangboot.xml",
				"E:\\maven\\game\\web\\src\\main\\resources\\spring\\majiangcalculator.xml",
				"E:\\maven\\game\\web\\src\\main\\resources\\spring\\majiangexecutor.xml",
				"E:\\maven\\game\\web\\src\\main\\resources\\spring\\majianghujudger.xml",
				"E:\\maven\\game\\web\\src\\main\\resources\\spring\\ningbomajiang4baida.xml",
				"E:\\maven\\game\\web\\src\\main\\resources\\spring\\ningbomajiang7baida.xml",
				"E:\\maven\\game\\web\\src\\main\\resources\\spring\\ningbomajiang3baida.xml" };
		ApplicationContext ac = new FileSystemXmlApplicationContext(configs);
		Server server = (Server) ac.getBean("gameServer");
		
		Room room = server.createRoom("player0", createRoomInfo());
		// server.leaveRoom("player0", room.getId());
		server.enterRoom("player1", room.getId());
		server.enterRoom("player2", room.getId());
//		server.leaveRoom("player1", room.getId());
//		server.enterRoom("player1", room.getId());
//		server.leaveRoom("player0", room.getId());
//		server.enterRoom("player0", room.getId());
//		server.playerReady("player2", room.getId());
		// server.playerUnready("player2", room.getId());
		server.enterRoom("player3", room.getId());
		// server.leaveRoom("player2", room.getId());
		// Room room = channel.createRoom("player0", createRoomInfo());
		// Channel channel = server.getChannel("ningboMajiang");
		// room.addPlayer("player1");
		// room.addPlayer("player2");
		// room.addPlayer("player3");

		Scanner scanner = new Scanner(System.in);
		while (true) {
			String next = scanner.nextLine();
			// logger.info("next is " + next);
			if (next.contains("107")) {
				server.playerReady("player" + next.split(" ")[0], room.getId());
				continue;
			}
			try {
				MajiangAction majiangAction = parse(next, server, room.getId());
				if (majiangAction != null) {
					room.currentGame().nextAction(majiangAction);
				}
			} catch (Exception e) {
				logger.error("### error,", e);
				continue;
			}
			// logger.info("one cycle end");
		}

	}
}
