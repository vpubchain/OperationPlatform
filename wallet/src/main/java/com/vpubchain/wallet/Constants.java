package com.vpubchain.wallet;

import android.text.format.DateUtils;

import com.google.common.collect.EnumHashBiMap;
import com.vpubchain.core.coins.AsiacoinMain;
import com.vpubchain.core.coins.AuroracoinMain;
import com.vpubchain.core.coins.BatacoinMain;
import com.vpubchain.core.coins.BitcoinMain;
import com.vpubchain.core.coins.EthMain;
import com.vpubchain.core.coins.SuqaMain;
import com.vpubchain.core.coins.SuqaTest;
import com.vpubchain.core.coins.BlackcoinMain;
import com.vpubchain.core.coins.BurstMain;
import com.vpubchain.core.coins.CanadaeCoinMain;
import com.vpubchain.core.coins.CannacoinMain;
import com.vpubchain.core.coins.ClamsMain;
import com.vpubchain.core.coins.ClubcoinMain;
import com.vpubchain.core.coins.CoinID;
import com.vpubchain.core.coins.CoinType;
import com.vpubchain.core.coins.DashMain;
import com.vpubchain.core.coins.DigibyteMain;
import com.vpubchain.core.coins.DigitalcoinMain;
import com.vpubchain.core.coins.DogecoinMain;
import com.vpubchain.core.coins.DogecoinTest;
import com.vpubchain.core.coins.EguldenMain;
import com.vpubchain.core.coins.FeathercoinMain;
import com.vpubchain.core.coins.GcrMain;
import com.vpubchain.core.coins.GuldenMain;
import com.vpubchain.core.coins.JumbucksMain;
import com.vpubchain.core.coins.LitecoinMain;
import com.vpubchain.core.coins.LitecoinTest;
import com.vpubchain.core.coins.MonacoinMain;
import com.vpubchain.core.coins.NamecoinMain;
import com.vpubchain.core.coins.NeoscoinMain;
import com.vpubchain.core.coins.NovacoinMain;
import com.vpubchain.core.coins.NuBitsMain;
import com.vpubchain.core.coins.NuSharesMain;
import com.vpubchain.core.coins.NxtMain;
import com.vpubchain.core.coins.OKCashMain;
import com.vpubchain.core.coins.ParkbyteMain;
import com.vpubchain.core.coins.PeercoinMain;
import com.vpubchain.core.coins.PotcoinMain;
import com.vpubchain.core.coins.ReddcoinMain;
import com.vpubchain.core.coins.RubycoinMain;
import com.vpubchain.core.coins.ShadowCashMain;
import com.vpubchain.core.coins.UroMain;
import com.vpubchain.core.coins.VergeMain;
import com.vpubchain.core.coins.VertcoinMain;
import com.vpubchain.core.coins.VpncoinMain;
import com.vpubchain.core.coins.RichcoinMain;
import com.vpubchain.core.network.CoinAddress;
import com.vpubchain.stratumj.ServerAddress;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author John L. Jegutanis
 * @author Andreas Schildbach
 */
public class Constants {

    //助记词数量  128 = 12
    public static final int SEED_ENTROPY_DEFAULT = 128;
    public static final int SEED_ENTROPY_EXTRA = 256;

    public static final String ARG_SEED = "seed";
    public static final String ARG_PASSWORD = "password";
    public static final String ARG_SEED_PASSWORD = "seed_password";
    public static final String ARG_EMPTY_WALLET = "empty_wallet";
    public static final String ARG_SEND_TO_ADDRESS = "send_to_address";
    public static final String ARG_SEND_TO_COIN_TYPE = "send_to_coin_type";
    public static final String ARG_SEND_TO_ACCOUNT_ID = "send_to_account_id";
    public static final String ARG_SEND_VALUE = "send_value";
    public static final String ARG_TX_MESSAGE = "tx_message";
    public static final String ARG_COIN_ID = "coin_id";
    public static final String ARG_ACCOUNT_ID = "account_id";
    public static final String ARG_MULTIPLE_COIN_IDS = "multiple_coin_ids";
    public static final String ARG_MULTIPLE_CHOICE = "multiple_choice";
    public static final String ARG_SEND_REQUEST = "send_request";
    public static final String ARG_TRANSACTION_ID = "transaction_id";
    public static final String ARG_ERROR = "error";
    public static final String ARG_MESSAGE = "message";
    public static final String ARG_ADDRESS = "address";
    public static final String ARG_ADDRESS_STRING = "address_string";
    public static final String ARG_EXCHANGE_ENTRY = "exchange_entry";
    public static final String ARG_URI = "test_wallet";
    public static final String ARG_PRIVATE_KEY = "private_key";

    public static final int PERMISSIONS_REQUEST_CAMERA = 0;

    public static final String WALLET_FILENAME_PROTOBUF = "wallet";
    public static final long WALLET_WRITE_DELAY = 5;
    public static final TimeUnit WALLET_WRITE_DELAY_UNIT = TimeUnit.SECONDS;

    public static final long STOP_SERVICE_AFTER_IDLE_SECS = 30 * 60; // 30 mins

    public static final String HTTP_CACHE_NAME = "http_cache";
    public static final int HTTP_CACHE_SIZE = 256 * 1024; // 256 KiB
    public static final int NETWORK_TIMEOUT_MS = 15 * (int) DateUtils.SECOND_IN_MILLIS;

    public static final String TX_CACHE_NAME = "tx_cache";
    public static final int TX_CACHE_SIZE = 5 * 1024 * 1024; // 5 MiB, TODO currently not enforced

    public static final long RATE_UPDATE_FREQ_MS = 30 * DateUtils.SECOND_IN_MILLIS;

    /** Default currency to use if all default mechanisms fail. */
    public static final String DEFAULT_EXCHANGE_CURRENCY = "USD";

    public static final Charset UTF_8 = Charset.forName("UTF-8");
    public static final Charset US_ASCII = Charset.forName("US-ASCII");

    public static final char CHAR_HAIR_SPACE = '\u200a';
    public static final char CHAR_THIN_SPACE = '\u2009';
    public static final char CHAR_ALMOST_EQUAL_TO = '\u2248';
    public static final char CHAR_CHECKMARK = '\u2713';
    public static final char CURRENCY_PLUS_SIGN = '+';
    public static final char CURRENCY_MINUS_SIGN = '-';

    public static final String MARKET_APP_URL = "market://details?id=%s";
    public static final String BINARY_URL = "https://github.com/Coinomi/coinomi-android/releases";

    public static final String VERSION_URL = "https://coinomi.com/version";
    public static final String SUPPORT_EMAIL = "support@coinomi.com";

    public static final String LOGIN_TOKEN = "login_token";

    // 各个币种的server
    public static final List<CoinAddress> DEFAULT_COINS_SERVERS = ImmutableList.of(
            new CoinAddress(SuqaMain.get(),      new ServerAddress("mobile.suqa.org", 50001),
                                                    new ServerAddress("mobile.suqa.org", 50001)),

            new CoinAddress(BitcoinMain.get(),      new ServerAddress("btc-cce-1.coinomi.net", 5001),
                                                    new ServerAddress("btc-cce-2.coinomi.net", 5001)),
//            new CoinAddress(BitcoinTest.get(),      new ServerAddress("btc-testnet-cce-1.coinomi.net", 15001),
//                                                    new ServerAddress("btc-testnet-cce-2.coinomi.net", 15001)),
            new CoinAddress(DogecoinMain.get(),     new ServerAddress("doge-cce-1.coinomi.net", 5003),
                                                    new ServerAddress("doge-cce-2.coinomi.net", 5003)),
            new CoinAddress(DogecoinTest.get(),     new ServerAddress("doge-testnet-cce-1.coinomi.net", 15003),
                                                    new ServerAddress("doge-testnet-cce-2.coinomi.net", 15003)),
            new CoinAddress(LitecoinMain.get(),     new ServerAddress("ltc-cce-1.coinomi.net", 5002),
                                                    new ServerAddress("ltc-cce-2.coinomi.net", 5002)),
            new CoinAddress(LitecoinTest.get(),     new ServerAddress("ltc-testnet-cce-1.coinomi.net", 15002),
                                                    new ServerAddress("ltc-testnet-cce-2.coinomi.net", 15002)),
            new CoinAddress(PeercoinMain.get(),     new ServerAddress("ppc-cce-1.coinomi.net", 5004),
                                                    new ServerAddress("ppc-cce-2.coinomi.net", 5004)),
            new CoinAddress(NuSharesMain.get(),     new ServerAddress("nsr-cce-1.coinomi.net", 5011),
                                                    new ServerAddress("nsr-cce-2.coinomi.net", 5011)),
            new CoinAddress(NuBitsMain.get(),       new ServerAddress("nbt-cce-1.coinomi.net", 5012),
                                                    new ServerAddress("nbt-cce-2.coinomi.net", 5012)),
//            new CoinAddress(DashMain.get(),         new ServerAddress("drk-cce-1.coinomi.net", 5013),
//                    new ServerAddress("drk-cce-2.coinomi.net", 5013)),
            new CoinAddress(DashMain.get(),         //new ServerAddress("dnsseed4.jiulingo.net", 50001),
                                                    new ServerAddress("dnsseed4.jiulingo.net", 50001)),
            new CoinAddress(ReddcoinMain.get(),     new ServerAddress("rdd-cce-1.coinomi.net", 5014),
                                                    new ServerAddress("rdd-cce-2.coinomi.net", 5014)),
            new CoinAddress(BlackcoinMain.get(),    new ServerAddress("blk-cce-1.coinomi.net", 5015),
                                                    new ServerAddress("blk-cce-2.coinomi.net", 5015)),
            new CoinAddress(NamecoinMain.get(),     new ServerAddress("nmc-cce-1.coinomi.net", 5016),
                                                    new ServerAddress("nmc-cce-2.coinomi.net", 5016)),
            new CoinAddress(FeathercoinMain.get(),  new ServerAddress("ftc-cce-1.coinomi.net", 5017),
                                                    new ServerAddress("ftc-cce-2.coinomi.net", 5017)),
            new CoinAddress(RubycoinMain.get(),     new ServerAddress("rby-cce-1.coinomi.net", 5018),
                                                    new ServerAddress("rby-cce-2.coinomi.net", 5018)),
            new CoinAddress(UroMain.get(),          new ServerAddress("uro-cce-1.coinomi.net", 5019),
                                                    new ServerAddress("uro-cce-2.coinomi.net", 5019)),
            new CoinAddress(DigitalcoinMain.get(),  new ServerAddress("dgc-cce-1.coinomi.net", 5020),
                                                    new ServerAddress("dgc-cce-2.coinomi.net", 5020)),
            new CoinAddress(CannacoinMain.get(),    new ServerAddress("ccn-cce-1.coinomi.net", 5021),
                                                    new ServerAddress("ccn-cce-2.coinomi.net", 5021)),
            new CoinAddress(MonacoinMain.get(),     new ServerAddress("mona-cce-1.coinomi.net", 5022),
                                                    new ServerAddress("mona-cce-2.coinomi.net", 5022)),
            new CoinAddress(DigibyteMain.get(),     new ServerAddress("dgb-cce-1.coinomi.net", 5023),
                                                    new ServerAddress("dgb-cce-2.coinomi.net", 5023)),
            // 5024 primecoin
            new CoinAddress(ClamsMain.get(),        new ServerAddress("clam-cce-1.coinomi.net", 5025),
                                                    new ServerAddress("clam-cce-2.coinomi.net", 5025)),
            new CoinAddress(ShadowCashMain.get(),   new ServerAddress("sdc-cce-1.coinomi.net", 5026),
                                                    new ServerAddress("sdc-cce-2.coinomi.net", 5026)),
            new CoinAddress(NeoscoinMain.get(),     new ServerAddress("neos-cce-1.coinomi.net", 5027),
                                                    new ServerAddress("neos-cce-2.coinomi.net", 5027)),
            new CoinAddress(VertcoinMain.get(),     new ServerAddress("vtc-cce-1.coinomi.net", 5028),
                                                    new ServerAddress("vtc-cce-2.coinomi.net", 5028)),
            new CoinAddress(JumbucksMain.get(),     new ServerAddress("jbs-cce-1.coinomi.net", 5029),
                                                    new ServerAddress("jbs-cce-2.coinomi.net", 5029)),
            new CoinAddress(VpncoinMain.get(),      new ServerAddress("vpn-cce-1.coinomi.net", 5032),
                                                    new ServerAddress("vpn-cce-2.coinomi.net", 5032)),
            new CoinAddress(CanadaeCoinMain.get(),  new ServerAddress("cdn-cce-1.coinomi.net", 5033),
                                                    new ServerAddress("cdn-cce-2.coinomi.net", 5033)),
            new CoinAddress(NovacoinMain.get(),     new ServerAddress("nvc-cce-1.coinomi.net", 5034),
                                                    new ServerAddress("nvc-cce-2.coinomi.net", 5034)),
            new CoinAddress(ParkbyteMain.get(),     new ServerAddress("pkb-cce-1.coinomi.net", 5035),
                                                    new ServerAddress("pkb-cce-2.coinomi.net", 5035)),
            new CoinAddress(NxtMain.get(),          new ServerAddress("176.9.65.41", 7876),
                    new ServerAddress("176.9.65.41", 7876)),
            new CoinAddress(BurstMain.get(),        new ServerAddress("burst-cce-1.coinomi.net", 5051),
                                                    new ServerAddress("burst-cce-2.coinomi.net", 5051)),
            new CoinAddress(VergeMain.get(),        new ServerAddress("xvg-cce-1.coinomi.net", 5036),
                                                    new ServerAddress("xvg-cce-2.coinomi.net", 5036)),
            new CoinAddress(EguldenMain.get(),      new ServerAddress("efl-cce-1.coinomi.net", 5037),
                                                    new ServerAddress("efl-cce-2.coinomi.net", 5037)),
            new CoinAddress(GcrMain.get(),          new ServerAddress("gcr-cce-1.coinomi.net", 5038),
                                                    new ServerAddress("gcr-cce-2.coinomi.net", 5038)),
            new CoinAddress(PotcoinMain.get(),      new ServerAddress("pot-cce-1.coinomi.net", 5039),
                                                    new ServerAddress("pot-cce-2.coinomi.net", 5039)),
            new CoinAddress(GuldenMain.get(),       new ServerAddress("gulden-cce-1.coinomi.net", 5040),
                                                    new ServerAddress("gulden-cce-2.coinomi.net", 5040)),
            new CoinAddress(AuroracoinMain.get(),   new ServerAddress("aur-cce-1.coinomi.net", 5041),
                                                    new ServerAddress("aur-cce-2.coinomi.net", 5041)),
            new CoinAddress(BatacoinMain.get(),     new ServerAddress("bata-cce-1.coinomi.net", 5042),
                                                    new ServerAddress("bata-cce-1.coinomi.net", 5042)),
            new CoinAddress(OKCashMain.get(),       new ServerAddress("ok-cce-1.coinomi.net", 5043),
                                                    new ServerAddress("ok-cce-2.coinomi.net", 5043)),
            new CoinAddress(AsiacoinMain.get(),     new ServerAddress("ac-cce-1.coinomi.net", 5044),
                                                    new ServerAddress("ac-cce-2.coinomi.net", 5044)),
            new CoinAddress(ClubcoinMain.get(),     new ServerAddress("club-cce-1.coinomi.net", 5045),
                                                    new ServerAddress("club-cce-2.coinomi.net", 5045)),
            new CoinAddress(RichcoinMain.get(),     new ServerAddress("richx-cce-1.coinomi.net", 5046),
                                                    new ServerAddress("richx-cce-2.coinomi.net", 5046)),
            new CoinAddress(EthMain.get(),          new ServerAddress("dnsseed4.jiulingo.net", 50001))
    );

    //币种的图标
    public static final HashMap<CoinType, Integer> COINS_ICONS;
    //币种的区块浏览器
    public static final HashMap<CoinType, String> COINS_BLOCK_EXPLORERS;
    static {
        COINS_ICONS = new HashMap<>();
        COINS_ICONS.put(CoinID.BITCOIN_MAIN.getCoinType(), R.drawable.bitcoin);
        COINS_ICONS.put(CoinID.LITECOIN_MAIN.getCoinType(), R.drawable.litecoin);
        COINS_ICONS.put(CoinID.DASH_MAIN.getCoinType(), R.drawable.vpubchain);
        COINS_ICONS.put(CoinID.EthCoin_MAIN.getCoinType(),R.drawable.dogecoin);

        COINS_BLOCK_EXPLORERS = new HashMap<CoinType, String>();
        COINS_BLOCK_EXPLORERS.put(CoinID.LITECOIN_MAIN.getCoinType(), "http://ltc.blockr.io/tx/info/%s");
        COINS_BLOCK_EXPLORERS.put(CoinID.DASH_MAIN.getCoinType(), "http://www.vpubchain.net/insight/tx/%s");
    }

    public static final CoinType DEFAULT_COIN = SuqaMain.get();
    public static final List<CoinType> DEFAULT_COINS = ImmutableList.of((CoinType) SuqaMain.get());
    public static final ArrayList<String> DEFAULT_TEST_COIN_IDS = Lists.newArrayList(
            SuqaTest.get().getId()
    );

    //支持的币种
    public static final List<CoinType> SUPPORTED_COINS = ImmutableList.of(
            DashMain.get(),
            BitcoinMain.get(),
            LitecoinMain.get(),
            EthMain.get()
    );


}
