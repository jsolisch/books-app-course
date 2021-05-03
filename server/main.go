// Package main contains a android tutorial demo server. Note this is not a production ready serer.
package main

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"
	"strconv"
	"sync"
	"time"

	"github.com/google/uuid"
)

// Book holds the book data.
type Book struct {
	ID       uuid.UUID `json:"id"` // This is not suppose to be production ready it just supports the example. Normally you would use the IBAN as unique ID.
	Title    string    `json:"title"`
	Author   string    `json:"author"`
	ISBN     string    `json:"isbn"`
	Modified int64     `json:"modified"`
}

var modified = time.Date(2021, time.April, 0, 0, 0, 0, 0, time.UTC).UnixNano() / 100000
var books = []Book{
	Book{
		ID:       uuid.MustParse("dab1f04d-0039-4a73-ad75-fc5969af21d0"),
		Title:    "Fahrenheit 451",
		Author:   "Ray Bradbury",
		ISBN:     "9780006546061",
		Modified: modified,
	},
	Book{
		ID:       uuid.MustParse("123c7c53-7658-470d-b05e-d58e06c02e1c"),
		Title:    "The Animal Farm",
		Author:   "George Orwell",
		ISBN:     "9780141036137",
		Modified: modified,
	},
	Book{
		ID:       uuid.MustParse("93b46cbf-8cc9-4c3b-8f75-951cb47a309e"),
		Title:    "1984",
		Author:   "George Orwell",
		ISBN:     "0141036141",
		Modified: modified,
	},
}
var bookMux = sync.Mutex{}

func main() {
	mux := http.NewServeMux()
	mux.HandleFunc("/api/books", booksApi)
	srv := http.Server{
		Handler:      mux,
		Addr:         ":9001",
		WriteTimeout: 7 * time.Second,
		ReadTimeout:  7 * time.Second,
	}
	log.Printf("Starting server at: %s", srv.Addr)
	log.Printf("Error while running server, %v", srv.ListenAndServe())
}

func booksApi(w http.ResponseWriter, r *http.Request) {
	log.Printf("%s /api/books", r.Method)
	switch r.Method {
	case "GET":
		bookMux.Lock()
		defer bookMux.Unlock()
		since, _ := strconv.ParseInt(r.URL.Query().Get("since"), 10, 64)
		delta := make([]Book, 0)
		for _, book := range books {
			if book.Modified > since {
				delta = append(delta, book)
			}
		}
		json.NewEncoder(w).Encode(delta)
	case "POST":
		bookMux.Lock()
		defer bookMux.Unlock()
		defer r.Body.Close()
		var book Book
		if err := json.NewDecoder(r.Body).Decode(&book); err != nil {
			http.Error(w, "No book given", http.StatusBadRequest)
			return
		}
		for _, saved := range books {
			if saved.ID != book.ID && saved.Title == book.Title && saved.Author == book.Author {
				http.Error(w, "Book already exists", http.StatusConflict)
				return
			}
			if saved.ID == book.ID {
				w.WriteHeader(http.StatusCreated)
				return
			}
		}
		books = append(books, book)
	case "DELETE":
		bookMux.Lock()
		defer bookMux.Unlock()
		ID, err := uuid.Parse(r.URL.Query().Get("id"))
		if err != nil {
			http.Error(w, "No ID given", http.StatusBadRequest)
			return
		}
		for index, saved := range books {
			if saved.ID == ID {
				books = append(books[:index], books[index+1:]...)
				return
			}
			w.WriteHeader(http.StatusCreated)
		}
	default:
		http.Error(w, fmt.Sprintf("Http method '%s' is not supported.", r.Method), http.StatusMethodNotAllowed)
	}
}
