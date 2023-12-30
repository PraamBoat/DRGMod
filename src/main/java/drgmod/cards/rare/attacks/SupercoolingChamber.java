package drgmod.cards.rare.attacks;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import drgmod.actions.SupercoolingAction;
import drgmod.cards.BaseCard;
import drgmod.character.MinerCharacter;
import drgmod.util.CardStats;

public class SupercoolingChamber extends BaseCard {
    private static final int DAMAGE = 14;
    private static final int MAGIC = 3;


    public static final String ID = makeID(SupercoolingChamber.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MinerCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public SupercoolingChamber() {
        super(ID, info);
        setDamage(DAMAGE);
        setMagic(MAGIC);
//        setExhaust(true);
        this.misc = DAMAGE;
    }

    @Override
    public void upgrade(){
        super.upgrade();
        if(this.upgraded){
            upgradeBaseCost(1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        returnToHand = false;
//        addToBot(new SupercoolingAction(p, m, new DamageInfo(p, this.damage, this.damageTypeForTurn), SCALING, makeStatEquivalentCopy()));
        addToBot(new SupercoolingAction(p, m, new DamageInfo(p, this.damage, this.damageTypeForTurn), magicNumber, this));
//        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL)));
//        addToBot(new IncreaseMiscAction(uuid, misc, magicNumber));
//        setDamage(damage + misc);
//        if ((m.isDying || m.currentHealth <= 0) && !m.halfDead && !m.hasPower("Minion")){
//            addToBot(new ModifyDamageAction(this.uuid, this.magicNumber));
//            d
//        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.baseDamage = misc;
        initializeDescription();
    }

    @Override
    public float getTitleFontSize() {
        return 20f;
    }
}
