# 🐱 AstroCats: Task From the Citadel 🛸

Imagine a future where cats, and only cats, have evolved to become the dominant species of the cosmos.
In a forgotten corner of the multiverse, in Universe A175, humanity disappeared millennia ago, making way for a highly advanced feline civilization. But everything changed after the latest elections, the new president implemented a sinister project that assimilated the entire population into a collective mind, stealing cats' most precious essence, their individuality and creativity.
From the shadows of this oppressive regime, four rebel cats managed to escape just before final assimilation. Now stranded aboard a spaceship orbiting their home planet, they must face a critical mission: restoring the technological and ecological balance of their world before it's too late. And their only tool to accomplish this is Python programming!
Dive into this narrative and educational adventure where science fiction intertwines with programming logic. Through short but challenging missions, you'll guide these prodigious cats as they solve problems, hack corrupt systems, and uncover the secrets of code... all while seeking to reclaim freedom for their species.
Are you ready to join the feline resistance and help these furry heroes conquer the multiverse... one line of code at a time? The future of cats depends on you!

- 📹 Link video:  https://youtu.be/xS2F8G-gMxE
- 📁 AstroCats.zip:  https://drive.google.com/drive/folders/1Q8mX7UhCmbJo3TT7_PRYEPtszuevRIGN?usp=sharing


## How we built it

This game was developed in Java, combining AI-generated visuals with design work crafted using Adobe software.

-----------------------------------------------------

 _💡This project was developed by Team 94 for the CS Girls H.I. vs A.I. Hackathon - Make Anything, But Make it YOU (Beginner-Friendly Track)._

-----------------------------------------------------

# CODE

- 🐾 Problema 1: Code Rescue 

- 🛰️ Problem 2: *Optimize orbital jump*

- 🚀 Problem 3: *Assimilator Helm – Consciousness Teleport*

### 🐾 **Problem 1: Code Rescue**

**Context:** The rebel cats found critical code fragments to hack the assimilation system, but they're reversed! Each word in the code was encrypted backward to hide its purpose. Your mission is to create a program that restores the original code by reversing **each word individually**, keeping spaces in their correct positions.

**Example 1:**

```
Input: "tac eht yaw"
Output: "cat the way"

```

**Example 2:**

```
Input: "gnitset si siht"
Output: "testing is this"

```

**Expected solution (Python):**

```python
def rescue_code(s):
    return ' '.join(word[::-1] for word in s.split())

print(rescue_code("tac eht yaw"))  
print(rescue_code("gnitset si siht"))  

```

---

### 🛰️ **Problem 2: *Optimize Orbital Jump***

**Context:** To escape the planet, the spaceship must perform an "orbital jump" while consuming fuel efficiently. Each jump segment requires a specific amount of fuel, but the corrupt system demands unnecessary expenditures! Your task is to optimize consumption: **if a segment exceeds 10 fuel units, cap it at 10**. Calculate the total optimized fuel for the jump.

**Example 1:**

```
Input: [7, 15, 4]
Output: 21  # (7 + 10 + 4)

```

**Example 2:**

```
Input: [12, 3, 9, 11]
Output: 32  # (10 + 3 + 9 + 10)

```

**Expected solution (Python):**

```python
def optimize_orbital_jump(fuel_segments):
    return sum(min(segment, 10) for segment in fuel_segments)
print(optimize_orbital_jump([7, 15, 4]))    
print(optimize_orbital_jump([12, 3, 9, 11])) 

```

---

### 🚀 **Problem 3: *Assimilator Helm – Consciousness Teleport***

**Context:** After failing to save their planet and unable to complete the orbital jump, the cats activate the Assimilator Helm as a last resort to teleport their consciousness to another universe—one where humanity thrives. The helm encodes their essence into numbers representing human letters (A=1, B=2, ..., Z=26). Create a program that converts these numbers into text readable by humans.

**Example 1:**

```
Input: [3, 1, 20]
Output: "CAT"

```

**Example 2:**

```
Input: [20, 5, 19, 20]
Output: "TEST"

```

**Expected solution (Python):**

```python
def teleport_consciousness(codes):
    return ''.join(chr(num + 64) for num in codes)
print(teleport_consciousness([3, 1, 20]))     
print(teleport_consciousness([20, 5, 19, 20]))  
```
