package drgmod.cards.rare.skills;

import basemod.devcommands.draw.Draw;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import drgmod.actions.GainMineralsAction;
import drgmod.cards.BaseCard;
import drgmod.character.MinerCharacter;
import drgmod.powers.*;
import drgmod.util.CardStats;
import drgmod.util.CustomTags;

public class BlackoutStout extends BaseCard {
    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 3;

    public static final String ID = makeID(BlackoutStout.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MinerCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public BlackoutStout() {
        super(ID, info);
        setMagic(MAGIC, UPG_MAGIC);
        tags.add(CustomTags.ALCOHOL);
        setExhaust(true);
    }

//    @Override
//    public void upgrade() {
//        super.upgrade();
//        if(this.upgraded){
//            upgradeBaseCost(0);
//        }
//    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,  p, new IntoxicationPower(p, 5)));
        if (this.upgraded){
            addToBot(new DrawCardAction(p, magicNumber));
        }
//        addToBot(new ApplyPowerAction(p,  p, new IntoxicationPower(p, 5)));
        addToBot(new ApplyPowerAction(p, p, new BlackoutPower(p, 1)));
    }
}
