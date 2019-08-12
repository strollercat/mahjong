package com.nbcb.majiang.rule.judger.hu;

import com.nbcb.majiang.card.MajiangHuCards;
import com.nbcb.majiang.game.MajiangGame;
import com.nbcb.majiang.rule.judger.hu.type.HuType;
import com.nbcb.majiang.user.MajiangPlayer;

public interface MajiangHuJudger {

	public static final String HAIDILAOYUE = "海底捞月";

	public static final String DADIAO = "大吊车";

	public static final String DANDIAO = "单吊";

	public static final String DNXB = "东南西北";

	public static final String DUIDAO = "对倒";

	public static final String DUIDUIHU = "对对胡";

	public static final String FENGYISE = "风一色";

	public static final String GANG = "杠";

	public static final String GANGTOUKAIHUA = "杠头开花";

	public static final String HUA = "花";

	public static final String HUANDA = "还搭";

	public static final String HUNYISE = "混一色";

	public static final String KAZHANG = "卡张";

	public static final String MENGQING = "门清";

	public static final String PAIHU = "排胡";

	public static final String PAODA = "抛搭";

	public static final String QINGYISE = "清一色";

	public static final String SANBAIDA = "三百搭";

	public static final String SIBAIDA = "四百搭";

	public static final String WUDA = "无搭";

	public static final String ZFB = "中发白";

	public static final String ZIMO = "自摸";

	/**
	 * 
	 * 
	 * 接下来的都是没有具体hujudger类型的
	 * 
	 */
	public static final String WEIFENG = "位风";

	public static final String QUANFENG = "圈风";

	public static final String QINGFENG = "清风";

	public static final String LUANFENG = "乱风";

	public static final String QINGLAOTOU = "清老头";

	public static final String LUANLAOTOU = "乱老头";

	public static final String QIANGGANGHU = "抢杠胡";

	public static final String PENG = "碰";

	public static final String DUIZI = "对子";

	public static final String PIAOCAI = "漂财";

	public static final String QIDUIZI = "七对子";

	public static final String DADAHU = "达达胡";

	public static final String LAJIHU = "垃圾胡";

	public String getName();

	public HuType judge(MajiangGame mjGame, MajiangPlayer mjPlayer, int huType,
			MajiangHuCards mjHuCards);
}
