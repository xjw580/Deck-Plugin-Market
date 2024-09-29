import club.xiaojiawei.DeckStrategy
import club.xiaojiawei.bean.Card
import club.xiaojiawei.enums.RunModeEnum
import club.xiaojiawei.status.War
import java.util.HashSet

/**
 * @author 肖嘉威
 * @date 2024/9/29 16:58
 */
class TemplateStrategyDeck : DeckStrategy() {

    override fun name(): String {
//        套牌策略名
        return "偶数萨"
    }

    override fun getRunMode(): Array<RunModeEnum> {
//        策略允许运行的模式
        return arrayOf(RunModeEnum.WILD)
    }

    override fun deckCode(): String {
        return "套牌代码"
    }

    override fun id(): String {
        return "套牌唯一id"
    }

    override fun executeChangeCard(cards: HashSet<Card>) {
//        TODO("执行换牌策略")
        for (card in cards) {
            if (card.cost > 2){
//                不要哪张牌就直接移除
                cards.remove(card)
            }
        }
    }

    /**
     * 计算卡牌血量
     */
    fun calcBlood(card: Card): Int {
        return card.health + card.armor - card.damage
    }

    override fun executeOutCard() {
//        TODO("执行出牌策略")
        //        我方玩家
        val me = War.me
//        敌方玩家
        val rival = War.rival
        me?.let {
//            获取战场信息

//            获取我方所有手牌
            val handCards = me.handArea.cards
//            获取我方所有场上的卡牌
            val playCards = me.playArea.cards
//            获取我方英雄
            val hero = me.playArea.hero
//            获取我方武器
            val weapon = me.playArea.weapon
//            获取我方技能
            val power = me.playArea.power
//            获取我方所有牌库中的卡牌
            val deckCards = me.deckArea.cards
//            我方当前可用水晶数
            val usableResource = me.usableResource

//            cardId是游戏写死的，每张牌的cardId都是唯一不变的，如同身份证号码，
            val heroCardId = hero?.cardId
//            entityId在每局游戏中是唯一的
            val heroEntityId = hero?.entityId

            if (heroCardId == "ICC_833") {
                println("该卡牌为 冰霜女巫吉安娜")
            }

//            执行操作
            rival!!

//            攻击
            val copyPlayCards = playCards.toMutableList()

            for (playCard in copyPlayCards) {
                if (playCard.canAttack()) {
//                    判断我方随从攻击力是否大于敌方英雄血量
                    if (playCard.atc >= calcBlood(rival.playArea.hero!!)) {
//                    我方随从攻击敌方英雄
                        playCard.action.attackHero()
                    } else {
                        if (rival.playArea.cards.isNotEmpty()){
                            var rivalPlayCard = rival.playArea.cards[0]
//                            如果对方随从拥有嘲讽
                            if (rivalPlayCard.isTaunt){
//                                我方随从攻击敌方随从
                                playCard.action.attack(rivalPlayCard)
                            }
                        }

                    }
                }
            }

//            出牌
            val copyHandCards = handCards.toMutableList()
            for (handCard in copyHandCards) {
//                费用够
                if (handCard.cost <= me.usableResource){
//                    直接打出
//                    handCard.action.power()
//                    打出到我方场上指定下标处
//                    handCard.action.power(1)
//                    打出到我方场上指定卡牌处
//                    handCard.action.power(card)
//                    如果有比较复杂的卡牌
//                    如果是阿莱克丝塔萨（生命值变15的那个）
                    if (handCard.cardId == "EX1_561"){
//                        步骤：打出，指向敌方英雄（即战吼目标为敌方英雄）
                        handCard.action
                            .power()
                            ?.pointTo(rival.playArea.hero!!)
                    }
                }
            }
        }
    }

    override fun executeDiscoverChooseCard(vararg cards: Card): Int {
//        TODO("执行选择发现牌策略")
//        返回选择卡牌的下标，这里选择的第一张牌
        return 0
    }

}