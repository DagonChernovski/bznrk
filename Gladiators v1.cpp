// GladiatorFight.cpp : Этот файл содержит функцию "main". Здесь начинается и заканчивается выполнение программы.
//

#include <iostream>
#include "windows.h"
#include <stdint.h>
#include "SFML\Graphics.hpp"

using namespace std; 

const int HP = 50;
string* WEAPONS = new string[]{ "Spear","Tridence","Retia","Pugio","Double Sable" };
string* DEFENCE = new string[]{"Lats", "Shield", "NoProtect"};

class gladSprite {
    sf::Image image;
    sf::Sprite sprite;
    sf::Texture texture;
};
class AttackBehavior {
private:
    string name;
public:
    string getName() {
        return name;
    };
    void setName(string n) {
        name = n;
    }
    AttackBehavior() { name = "noAtk"; srand(time(NULL));
    };
    float attack() {
        return rand() % 10 + 5;
    }
};

class ProtectBehavior {
protected:
    string name;
public:
    ProtectBehavior() {
    };
    float protect(AttackBehavior* a);
    string getName() {
        return name;
    };
    void setName(string name) {
        this->name = name;
    }
};

class MediatorAttackProtect
{
public:
    float getKoefProtection(AttackBehavior* a, ProtectBehavior* b)
    {
        std::string wName = a->getName();
        string pName = b->getName();
        if (pName == "Avoid" && wName!="Retia") return 0;
        if (wName == "Spear")
        {
            if (pName == "Lats") return 0.5f;
            if (pName == "Shield") return 0.2f;
            if (pName == "NoProtect") return 1.5f;
        }
        if (wName == "Tridence")
        {
            if (pName == "Lats") return 0.4F;
            if (pName == "Shield") return 0.2F;
            if (pName == "NoProtect") return 0.7F;
        }
        if (wName == "Retia")
        {
            if (pName == "Lats") return 0.4;
            if (pName == "Shield") return 0.3;
            if (pName == "NoProtect") return 0.8;
            if (pName == "Avoid") return 1.2;
        }
        if (wName == "Pugio")
        {
            if (pName == "Lats") return 1.2;
            if (pName == "Shield") return 0;
            if (pName == "NoProtect") return 0.6;
        }
        if (wName == "Double Sable")
        {
            if (pName == "Lats") return 0.3;
            if (pName == "Shield") return 0.6;
            if (pName == "NoProtect") return 1.2;
        }
        return 1;
    }
};

float ProtectBehavior::protect(AttackBehavior* a) {
    MediatorAttackProtect* m=new MediatorAttackProtect();
    float koef;
    if (a->getName() == "Shield") {
        srand(time(NULL));
        float chance = 5;
        if (rand() % 10 > chance) {
            koef = m->getKoefProtection(a, this);
            return koef;
        }
        else
        {
            a->setName("NoProtect");
            koef = m->getKoefProtection(a, this);
            a->setName("Shield");
            return koef;
        }
    }
    koef = m->getKoefProtection(a, this);
    return koef;
};

class Lats : public ProtectBehavior {
public:
    Lats() {
        name = "Lats";
    }
};

class NoProtect : public ProtectBehavior {
    MediatorAttackProtect* m;
public:
    NoProtect() {
        name = "NoProtect";
    }
    float protect(AttackBehavior* a) {
        float koef = m->getKoefProtection(a, this);
        return koef;
    }
};
class Shield : public NoProtect {
    MediatorAttackProtect* m;
public:
    Shield() {
        name = "Shield";
    }
    float protect(AttackBehavior* a) {
        srand(time(NULL));
        float chance = 5;
        float koef;
        if (rand() % 10 > chance) { koef = m->getKoefProtection(a, this); printf("%d\% of damage blocked by Shield!", koef*100); }
        else koef = NoProtect::protect(a);
        return koef;
    }
};

class Avoid : public NoProtect {
    MediatorAttackProtect* m;
public:
    Avoid() {
        name = "Avoid";
    }
    float protect(AttackBehavior* a) {
        srand(time(NULL));
        float koef;
        if (rand() % 100 < 25) koef = m->getKoefProtection(a, this);
        else koef = NoProtect::protect(a);
    }
};


class Gladiator {
protected:
    int health;
    AttackBehavior* a=new AttackBehavior();
    ProtectBehavior* b=new ProtectBehavior();
    bool multipleWeapons = false;
    bool playable = false;
public:
    int *weapons = new int[] {0, 0, 0};
    std::string name = "Unnamed";
    Gladiator* opponent;
    bool isAlive, avoiding = false, onGuard = false;
    void setOpponent(Gladiator* opponent)
    {
        this->opponent = opponent;
        opponent->opponent = this;
        this->playable = true;
        opponent->playable = false;
    }
    Gladiator() 
    { 
        //printf("Main constructor called\n");
        health = HP; 
        a->setName("Spear"); 
        b->setName("NoProtect");
        //printf("names have been set\n");
    }
    Gladiator(std::string type, string armor) 
    {
        isAlive = true;
        health = HP;
        a->setName(type);
        b->setName(armor);
        srand(time(NULL));
    }
    void strike() {
        cout << "Gladiator " << name << " assaults his enemy with his " << a->getName() << "!\n";
        opponent->getHit(a);
    }
    string getName() {
        return name;
    }
    void battleInterface()
    {
        cout << "A glorious fight awaits our tribunes!\nOur fellow " << this->name << " has challenged " <<
            opponent->name << " into a gladiator battle!\n";
        while (isAlive && opponent->isAlive) {
            cout << "\nHP: \n" << name << " -- [" << health << '|' << HP << "]\n"
                << opponent->name << "-- [" << opponent->health << "|" << HP << "]\n";
            bool choiceScreen = true;
            int f;
            cout << "Your turn: \n1 - Strike\n2 - Protect\n3 - Change weapon\n";
            do cin >> f; while (makeTurn(f));
            if (!opponent->isAlive) break;
            else {
                f = rand() % 2 + 1;
                opponent->makeTurn(f);
                if (!isAlive) break;
            }
        }
    };
    void display();
    bool makeTurn(int f) {
        bool ret = true;
        switch (f) {
        case 1: strike(); ret = false; break;
        case 2:
            if (b->getName() == "NoProtect") {
                b = new Avoid();
                cout << name << " attempts to avoid the ongoing hit.\n";
            }
            else {
                onGuard = true;
                cout << name << " is on guard.\n";
            }
            ret = false;
            break;
        case 3:
            if (name != "Retiary") cout << "You are one-weaponed!";
            else {
                shiftWeapon();
                ret = false;
                break;
            }
        default: break;
        }
        return ret;
    };
    void setAttackBehavior(AttackBehavior* at) {
        a = at;
    };
    void setAttackBehavior(string s) {
        a->setName(s);
    }
    float getProtect() { return 1; }
    void getHit(AttackBehavior* a) {
        int dmg = a->attack() * b->protect(a);
        if (onGuard) dmg /= 2;
        onGuard = false;
        Sleep(500);
        cout << dmg << " of damage to " << name << "!\n";
        health -= dmg;
        if (b->getName() == "Avoid") b = new NoProtect();
        if (health <= 0) {
            Sleep(500);
            //Die();
            cout << name << " falls in defeat!\n"; isAlive = false; 
        }
    };
    void shiftWeapon() {
        int f;
        for (int i = 0; i < 3; i++) {
            cout << i+1 << " - " << WEAPONS[weapons[i]] << endl;
        }
        cin >> f;
        if (f>0 && f<3) setAttackBehavior(WEAPONS[weapons[f-1]]);

    }
};

class Retiary : public Gladiator {
public:
    Retiary() {
        srand(time(NULL));
        isAlive = true;
        name = "Retiary";
        a->setName("Retia");
        for (int i = 0; i < 3; i++) {
            weapons[i] = i + 1;
        }
        b = new Lats();
    }
    void strike() {
            opponent->getHit(a);
    }
    void display() {

    }
    void getHit(AttackBehavior* a) {
        int dmg = a->attack() * b->protect(a);
        if (onGuard) dmg /= 2;
        onGuard = false;
        cout << dmg << " of damage to " << name << "!\n";
        health -= dmg;
        if (b->getName() == "Avoid") b = new NoProtect();
        if (health <= 0) {
            //Die();
            cout << name << " falls in defeat!\n"; isAlive = false;
        }
    };
};
class Gall : public Gladiator {
public:
    Gall() {
        isAlive = true;
        name = "Gall";
        a->setName("Spear");
        b = new Shield();
        //b->setName("Shield");
    }
    float getProtect() { return b->protect(a); };
};
class Bestiary : public Gladiator {
public:
    Bestiary() {
        isAlive = true;
        name = "Bestiary";
        a->setName("Spear");
        b = new NoProtect();
        //b->setName("Shield");
    }
    float getProtect() { return b->protect(a); };
};
class Dimacher : public Gladiator {
public:
    Dimacher() {
        isAlive = true;
        name = "Dimacher";
        a->setName("Double Sable");
        b = new Shield();
    };
    void getHit(AttackBehavior* a) {
        int dmg = a->attack() * b->protect(a);
        if (onGuard) dmg /= 2;
        onGuard = false;
        cout << dmg << " of damage to " << name << "!\n";
        health -= dmg;
        if (b->getName() == "Avoid") b = new NoProtect();
        if (health <= 0) {
            //Die();
            cout << name << " falls in defeat!\n"; isAlive = false;
        }
    };
};

int main()
{
    Retiary ret, ret2;
    Dimacher dim, dim2;
    Gall gal, gal2;
    Gladiator* bestiary = new Gladiator("Spear", "NoProtect");
    Gladiator yourGlad, yourEnemy;
    int screen = 0;
    int inputN;
    char inputC;
    bool rightInput=false;
    while (screen == 0)
    {
        while (!rightInput) {
            cout << "Choose your fighter:\n[1] - Retiary\n[2] - Dimacher\n[3]- Gall\n[4] - Bestiary\n";
            cin >> inputN; rightInput = true;
            switch (inputN)
            {
            case 1: yourGlad = ret; break;
            case 2: yourGlad = dim; break;
            case 3: yourGlad = gal; break;
            case 4: yourGlad = *bestiary; break;
            default: rightInput = false; cout << "Wrong input!"; break;
            }
        }
        system("CLS"); rightInput = false;
        srand(time(NULL));
        while (!rightInput) {
            cout << "Choose your opponent:\n[1] - Retiary\n[2] - Dimacher\n[3] - Gall\n[4] - Bestiary\n[5] - Random\n";
            cin >> inputN; rightInput = true;
            if (inputN == 5) inputN = rand() % 4 + 1;
            switch (inputN)
            {
            case 1: yourEnemy = ret; break;
            case 2: yourEnemy = dim; break;
            case 3: yourEnemy = gal; break;
            case 4: yourEnemy = *bestiary; break;
            default: rightInput = false; cout << "Wrong input!"; break;
            }
        }
        rightInput = false;
        while (!rightInput) {
            system("CLS");
            cout << yourGlad.getName() << " VS. " << yourEnemy.getName() << endl;
            cout << "Is that acceptable? Y/N - - - ";
            cin >> inputC;
            if (inputC == 'Y' || inputC == 'N')
                rightInput = true;
        }
        if (inputC == 'Y') screen++;
        else continue;
    }
    yourGlad.setOpponent(&yourEnemy);
    yourGlad.display();
}
