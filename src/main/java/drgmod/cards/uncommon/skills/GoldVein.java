package drgmod.cards.uncommon.skills;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Flex;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import drgmod.actions.GainMineralsAction;
import drgmod.actions.MineralSpendAction;
import drgmod.cards.BaseCard;
import drgmod.character.MinerCharacter;
import drgmod.powers.*;
import drgmod.util.CardStats;
import drgmod.util.CustomTags;

public class GoldVein extends BaseCard {
    private static final int MAGIC = 10;
    private static final int UPG_MAGIC = 5;
//    private static final int MINERAL_GAIN = 10;
//    private static final int MINERAL_UPG = 5;
    private static final int DRAW = 1;

    public static final String ID = makeID(GoldVein.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MinerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public GoldVein() {
        super(ID, info);
        setMagic(MAGIC, UPG_MAGIC);
//        setCustomVar("mineralGain", MINERAL_GAIN, MINERAL_UPG);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainMineralsAction(p, magicNumber));
        addToBot(new DrawCardAction(customVar("cardDraw")));
    }

    @Override
    public void applyPowers(){
        super.applyPowers();
        if (AbstractDungeon.player.hasPower(MineralPower.POWER_ID)){
            int mineralCount = AbstractDungeon.player.getPower(MineralPower.POWER_ID).amount + magicNumber;
            if (mineralCount > 80){
                mineralCount = 80;
            }
            setCustomVar("cardDraw", (mineralCount) / 15 * DRAW);
        }
        else{
            setCustomVar("cardDraw", magicNumber / 15 * DRAW);
        }
        initializeDescription();
    }
}
