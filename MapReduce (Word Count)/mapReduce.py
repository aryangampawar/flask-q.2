import re
from collections import defaultdict


# --- Step 1: Mapper Function ---
def mapper(document):
  """
  Reads a document, cleans it, and emits (word, 1) for each word.
  """
  text = document.lower()
  text = re.sub(r"[^ws]", "", text)
  words = text.split()
  for word in words:
    if word:
      yield (word, 1)


# --- Step 2: Reducer Function ---
def reducer(key, values):
  """
  Sums the values for a given key.
  """
  total = sum(values)
  yield (key, total)


if __name__ == "__main__":
  documents = [
    "Data science is the future of technology and business intelligence.",
    "Machine learning algorithms can analyze massive amounts of data efficiently.",
    "Big data analytics helps companies make better business decisions.",
    "Python programming is essential for data science and machine learning projects.",
  ]

  print("--- Input Documents ---")
  for doc in documents:
    print(f"- {doc}")
  print("
" + "=" * 30 + "
")

  print("--- 1. Map Phase ---")
  mapped_pairs = []
  for doc in documents:
    for pair in mapper(doc):
      mapped_pairs.append(pair)
      print(f"  Mapper output: {pair}")
  print("
" + "=" * 30 + "
")

  print("--- 2. Shuffle & Sort Phase (Grouping) ---")
  shuffled_data = defaultdict(list)
  for key, value in mapped_pairs:
    shuffled_data[key].append(value)
  for key, values in sorted(shuffled_data.items()):
    print(f"  Grouped: ('{key}', {values})")
  print("
" + "=" * 30 + "
")

  print("--- 3. Reduce Phase ---")
  final_counts = {}
  for key, values in sorted(shuffled_data.items()):
    for result_key, result_value in reducer(key, values):
      final_counts[result_key] = result_value
      print(f"  Reducer output: ('{result_key}', {result_value})")
  print("
" + "=" * 30 + "
")

  print("--- Final Word Count Results ---")
  sorted_results = sorted(final_counts.items(), key=lambda item: item[1], reverse=True)
  for word, count in sorted_results:
    print(f"{word:<15} {count}")
