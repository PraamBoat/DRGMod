package drgmod.cards.uncommon.attacks;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import drgmod.actions.ArmskoreCoilGunAction;
import drgmod.actions.ColetteWaveCoolerAction;
import drgmod.cards.BaseCard;
import drgmod.character.MinerCharacter;
import drgmod.powers.BurnPower;
import drgmod.powers.FreezePower;
import drgmod.powers.MineralPower;
import drgmod.powers.ShockPower;
import drgmod.util.CardStats;

public class ColetteWaveCooler extends BaseCard {
    private static final int DAMAGE = 0;
    private static final int MAGIC = 10;

    public static final String ID = makeID(ColetteWaveCooler.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MinerCharacter.Enums.CARD_COLOR,
            AbstractCard.CardType.ATTACK,
            AbstractCard.CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            AbstractCard.CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public ColetteWaveCooler() {
        super(ID, info);
        setExhaust(true);
        setMagic(MAGIC);
        setDamage(DAMAGE);
        setCustomVar("totalDebuffs", 0, 0);
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        super.calculateCardDamage(m);
        int totalDebuff = 0;
        for (AbstractPower k: m.powers){
            if (k.type == AbstractPower.PowerType.DEBUFF){
                totalDebuff += k.amount;
            }
        }
        if (upgraded){
            totalDebuff *= 2;
        }
        setDamage(totalDebuff);
        initializeDescription();
    }

    @Override
    public float getTitleFontSize() {
        return 20f;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ColetteWaveCoolerAction(upgraded, m));
    }
}
