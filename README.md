# README
Googleカレンダーと連携して、設定した期間内で空いている時間を検索するSlackBotです。

## 設定
アプリを利用するには次の工程を行ってください。

1. `src/main/resource`にgoogleAPIのauthトークン(`credentials.json`)を入れる。
2. チャンネルの作成
3. SlackBotの作成
4. `Config.java`の設定
5. `Main.java`を実行

## 未来のあなたへ
雑に書いたコードなので、javadocもないし、メソッドは長いし、クラス間の依存は深いです。またテストも書いていないし、恐らくバグもありまくります。

機能追加してください。テストも追加してください。リファクタリングもしてください。よろしくお願いいたします。