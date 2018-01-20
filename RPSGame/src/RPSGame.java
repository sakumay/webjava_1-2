import java.util.Random;
import java.util.Scanner;

public class RPSGame {

  private static final boolean endressBattleFlg = true;//無限対戦用フラグ　無限：true
  private static final int battleMax = 10;//対戦回数
  static int beforeSetId = 0;

  public static void main(String[] args){

    String endMassage = "\r\n\r\n  じゃんけんゲームを終了します";
    boolean continueFlg = true;

    System.out.println("◆◆じゃんけんゲームスタート◆◆");

    while(continueFlg){

      String userInput = setInputData(1,0);

      switch(userInput) {
        case "m":
          manualGame();
        break;

        case "a":
          autoGame();
        break;

        case "b":
          anotherSystemGame();
        break;

        case "e":
        continueFlg = false;
        break;

        default:
        break;
      }

    }
    System.out.println(endMassage);
  }



  /**
   * システム内の各入力を取得する。パターンに応じて、コマンドの再入力を求める。
   * @return 入力データ
   */
  private static String setInputData(int version, int count) {
    String Input = null;
    boolean goFlg = true;
    boolean errorMassageFlg = false;

    switch(version) {
      case 1:
        do {
        /*メインメニューを表示*/
        showStartMassage();

        if(errorMassageFlg) {
          System.out.println("\r\n[!]入力コマンドが正しくありません[!]\r\n");
        }

        System.out.print("\r\nコマンドを入力してください　＞ ");
        Input = getInputData();
        goFlg = false;
        switch(Input) {
          case "m":
          break;
          case "a":
          break;
          case "b":
          break;
          case "e":
          break;

          default:
            errorMassageFlg = true;
            goFlg = true;
          break;
        }
      }while(goFlg);
      break;

      case 2:
        do {
          showManualBattleMassage(count);

        if(errorMassageFlg) {
          System.out.println("\r\n[!]入力コマンドが正しくありません[!]\r\n");
        }

        System.out.print("\r\nコマンドを入力してください　＞ ");
        Input = getInputData();
        goFlg = false;
        switch(Input) {
          case "1":
          break;
          case "2":
          break;
          case "3":
          break;
          case "e":
          break;

          default:
            errorMassageFlg = true;
            goFlg = true;
          break;
        }
      }while(goFlg);
      break;

      default:
      break;
    }

    return Input;
  }


  private static void manualGame(){
    System.out.println("\r\nマニュアル対戦スタート");

    boolean continueFlg = true;
    int battleCount = 1;
    int winCount = 0;
    int loseCount = 0;
    String userResultMessage = "あなた｜";
    String comResultMessage = "ＣＰ　｜";
    String barMessage = "―――――";


    while(continueFlg){

      int comId = go();

      String userChoise = setInputData(2,battleCount);
      int userId = 1;
      String userRPSname;
      String comRPSname;

      battleCount ++;

      // 決定したNoより、出し手のIDを設定
      switch(userChoise) {
        case "1":
          userId = RPSType.ROCK.getId();
          break;
        case "2":
          userId = RPSType.SCISSORS.getId();
          break;
        case "3":
          userId = RPSType.PAPER.getId();
          break;
        case "e":
          continueFlg = false;
          break;
        default:
          System.out.println("\r\n[!]入力コマンドが正しくありません[!]");
          battleCount --;
          break;
        }


      userRPSname = exchangeIDtoNAME(userId);
      comRPSname = exchangeIDtoNAME(comId);

      System.out.print("あなた："+userRPSname+"　相手："+comRPSname+"\r\n");

      /*勝敗判定*/
      if(userId==comId) {
        //プリミティブ型の比較のため「equals」を使用せず「==」を使用する
        /*あいこだった場合*/
        System.out.println("\r\n結果：　あいこ");
        battleCount --; //対戦のカウントを戻す
      }else {
        /*Notあいこ*/
        int judeData = comId - userId;

        if(judeData == 1 || judeData == -2){
          /*自分が勝った場合*/
          System.out.println("\r\n結果：　あなたの勝利");
          userResultMessage = userResultMessage.concat("〇");
          comResultMessage = comResultMessage.concat("×");
          winCount ++;
        }else {
          /*自分が負けた場合*/
          System.out.println("\r\n結果：　あなたの負け");
          userResultMessage = userResultMessage.concat("×");
          comResultMessage = comResultMessage.concat("〇");
          loseCount ++;
        }
        barMessage = barMessage.concat("―");
      }
      System.out.println("\r\n");

      /*対戦回数の上限だった場合にループを終了*/
      if(continueFlg && battleCount > battleMax) {
        continueFlg = endressBattleFlg;
      }
    }
    battleCount --;
    /*対戦終了*/
    String resultMessage;
    if(winCount == loseCount) {
      resultMessage = "引き分けです";
    }else if(winCount > loseCount) {
      resultMessage = "あなたの勝利です";
    }else{
      resultMessage = "あなたの負けです";
    }

    System.out.println("＜対戦結果＞");
    System.out.print("\r\n"+battleCount+"回戦中　"+winCount+"勝　／　"+loseCount+"敗\r\n");
    System.out.print("\r\n　【"+resultMessage+"】\r\n\r\n");

    System.out.println(userResultMessage);
    System.out.println(barMessage);
    System.out.println(comResultMessage);

    System.out.print("\r\n\r\n\r\n");

  }

  private static void autoGame(){
    System.out.println("\r\nオート対戦スタート");
    System.out.println("\r\n");

    int maxBattleCount = 50;
    int battleCount = 1;
    int winCount = 0;
    int loseCount = 0;
    String userResultMessage = "ＣＰ１｜";
    String comResultMessage = "ＣＰ２｜";
    String barMessage = "―――――";

    /*対戦回数の上限だった場合にループを終了*/
    while(battleCount <= maxBattleCount){

      battleCount ++;
      int com1Id = go();
      int com2Id = go2();


      /*勝敗判定*/
      if(com1Id==com2Id) {
        //プリミティブ型の比較のため「equals」を使用せず「==」を使用する
        /*あいこだった場合*/
        beforeSetId = 0;
        battleCount --; //対戦のカウントを戻す
      }else {
        /*Notあいこ*/
        int judeData = com2Id - com1Id;

        if(judeData == 1 || judeData == -2){
          /*ＣＰ１が勝った場合*/
          userResultMessage = userResultMessage.concat("〇");
          comResultMessage = comResultMessage.concat("×");
          RSPPlayerBase.onResult(false);
          winCount ++;
        }else {
          /*ＣＰ２が勝った場合*/
          userResultMessage = userResultMessage.concat("×");
          comResultMessage = comResultMessage.concat("〇");
          RSPPlayerBase.onResult(true);
          loseCount ++;
        }
        barMessage = barMessage.concat("―");
      }
    }

    battleCount --;

    /*対戦終了*/
    String resultMessage;
    if(winCount == loseCount) {
      resultMessage = "引き分けです";
    }else if(winCount > loseCount) {
      resultMessage = "ＣＰ１の勝利です";
    }else{
      resultMessage = "ＣＰ２の勝利です";
    }

    System.out.println("＜対戦結果＞");
    System.out.print("\r\n"+battleCount+"回戦中　CP1："+winCount+"勝　／　CP2："+loseCount+"勝\r\n");
    System.out.print("\r\n　【"+resultMessage+"】\r\n\r\n");

    System.out.println(userResultMessage);
    System.out.println(barMessage);
    System.out.println(comResultMessage);

    System.out.print("\r\n\r\n\r\n");

  }


  private static void anotherSystemGame() {
    System.out.println("他システムとの対戦スタート");

    boolean continueFlg = true;
    int battleCount = 1;
    int winCount = 0;
    int loseCount = 0;
    String userResultMessage = "本システム｜";
    String comResultMessage = "他システム｜";
    String barMessage = "―――――――";


    while(continueFlg){

      int comId = go2();
      String comChoise = "";

      switch(comId) {
        case 1:
          comChoise = ("グー");
          break;
        case 2:
          comChoise = ("チョキ");
          break;
        default:
          comChoise = ("パー");
          break;
      }

      System.out.print("\r\n【じゃんけんゲームの出し手："+comId+" "+comChoise+" 】\r\n");

      String userChoise = setInputData(2,battleCount);
      int userId = 1;
//      int battleMax = 10;//対戦回数
      String userRPSname;
      String comRPSname;

      battleCount ++;

      // 決定したNoより、出し手のIDを設定
      switch(userChoise) {
        case "1":
          userId = RPSType.ROCK.getId();
          break;
        case "2":
          userId = RPSType.SCISSORS.getId();
          break;
        case "3":
          userId = RPSType.PAPER.getId();
          break;
        case "e":
          continueFlg = false;
          break;
        default:
          System.out.println("\r\n[!]入力コマンドが正しくありません[!]");
          battleCount --;
          break;
        }

      userRPSname = exchangeIDtoNAME(userId);
      comRPSname = exchangeIDtoNAME(comId);

      System.out.print("あなた："+userRPSname+"　相手："+comRPSname+"\r\n");

      /*勝敗判定*/
      if(userId==comId) {
        //プリミティブ型の比較のため「equals」を使用せず「==」を使用する
        /*あいこだった場合*/
        System.out.println("\r\n結果：　あいこ");
        beforeSetId = 0;
        battleCount --; //対戦のカウントを戻す
      }else {
        /*Notあいこ*/
        int judeData = comId - userId;

        if(judeData == 1 || judeData == -2){
          /*自分が勝った場合*/
          System.out.println("\r\n結果：　他システムの勝利");
          userResultMessage = userResultMessage.concat("〇");
          comResultMessage = comResultMessage.concat("×");
          RSPPlayerBase.onResult(false);
          winCount ++;
        }else {
          /*自分が負けた場合*/
          System.out.println("\r\n結果：　他システムの負け");
          userResultMessage = userResultMessage.concat("×");
          comResultMessage = comResultMessage.concat("〇");
          RSPPlayerBase.onResult(true);
          loseCount ++;
        }
        barMessage = barMessage.concat("―");
      }
      System.out.println("\r\n");

      /*対戦回数の上限だった場合にループを終了*/
      if(continueFlg && battleCount > battleMax) {
        continueFlg = endressBattleFlg;
      }
    }
    battleCount --;
    /*対戦終了*/
    String resultMessage;
    if(winCount == loseCount) {
      resultMessage = "引き分けです";
    }else if(winCount > loseCount) {
      resultMessage = "他システムの負けです";
    }else{
      resultMessage = "他システムの勝ちです";
    }

    System.out.println("＜対戦結果＞");
    System.out.print("\r\n"+battleCount+"回戦中　"+winCount+"勝　／　"+loseCount+"敗\r\n");
    System.out.print("\r\n　【"+resultMessage+"】\r\n\r\n");

    System.out.println(userResultMessage);
    System.out.println(barMessage);
    System.out.println(comResultMessage);

    System.out.print("\r\n\r\n\r\n");

  }


  private static String exchangeIDtoNAME(int choiseId) {
    // IDから手の内容を文字列化する
    String choiseName = "エラー";

    if(choiseId == 1) {
      choiseName = "ぐー";
    }else if(choiseId == 2) {
      choiseName = "ちょき";
    }else if(choiseId == 3) {
      choiseName = "ぱー";
    }
    return choiseName;
  }

  private static int go() {
    // 出し手の内容を決定
    Random rndNum = new Random();
    int comChoise = rndNum.nextInt(3);
    int comId = 1;

    // 決定したNoより、出し手のIDを設定
    switch(comChoise) {
      case 1:
        comId = RPSType.ROCK.getId();
        break;
      case 2:
        comId = RPSType.SCISSORS.getId();
        break;
      default:
        comId = RPSType.PAPER.getId();
        break;
    }
    return comId;
  }

  private static int go2() {

    int comId = 1;
    int comChoise =0;

    // 出し手の内容を決定
    if(beforeSetId == 0) {
      // 初回の場合、ランダムに出し手を決定
      Random rndNum = new Random();
      comChoise = rndNum.nextInt(3);
    }else {
      if(RSPPlayerBase.getResult()) {
        // 前回の対戦結果が「勝ち」の場合
        comChoise = beforeSetId - 1;
        if(comChoise == 0) {
          comChoise = 3;
        }
      }else {
        // 前回の対戦結果が「負け」の場合
        comChoise = beforeSetId - 1;
        if(comChoise == 0) {
          comChoise = 3;
        }
      }
    }

    // 決定したNoより、出し手のIDを設定
    switch(comChoise) {
      case 1:
        comId = RPSType.ROCK.getId();
        break;
      case 2:
        comId = RPSType.SCISSORS.getId();
        break;
      default:
        comId = RPSType.PAPER.getId();
        break;
    }

    beforeSetId = comId;

    return comId;
  }

  private static String getInputData() {
    Scanner scan = new Scanner(System.in);
    String input = scan.next();

    if(input.isEmpty()) {
      input = "b";
    }
    return input;
  }

  /**
   * じゃんけん出し手 列挙型
   */
  public enum RPSType {
    ROCK(1),      // グー
    SCISSORS(2),     // チョキ
    PAPER(3);  // パー

    private final int id;

    /**
     * コンストラクタ
     * @param id ID
     */
    private RPSType(final int id) {
      this.id = id;
    }

    /*
     * 列挙型のID取得
     * @return ID
     */
    public int getId() {
      return id;
    }


  }

  public static class RSPPlayerBase {

    private static boolean winnerData = true;


    public static void onResult(boolean isWinner) {
      winnerData = isWinner;

    }

    public static boolean getResult() {
      return winnerData;
    }


  }

  public static void showStartMassage() {
    /*メインメニューを表示*/
    System.out.println("\r\n＜じゃんけんゲームメニュー＞\r\n");
    System.out.println("マニュアルじゃんけん：　m");
    System.out.println("オートじゃんけん　　：　a");
    System.out.println("他システムとの対戦　：　b");
    System.out.println("ゲーム終了　　　　　：　e");
  }
  public static void showManualBattleMassage(int battleCount) {
    /*マニュアル対戦のメニューを表示*/
    System.out.print("\r\n[ "+battleCount);
    System.out.println("回目の対戦 ]");
    System.out.println("ぐー　　：　1");
    System.out.println("ちょき　：　2");
    System.out.println("ぱー　　：　3");
    System.out.println("対戦を終了　：　e");
  }


}


