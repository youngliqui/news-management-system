CREATE EXTENSION IF NOT EXISTS pg_trgm;

-- Index for the news table
CREATE INDEX idx_news_title_trgm ON news USING GIN (title gin_trgm_ops);
CREATE INDEX idx_news_text_trgm ON news USING GIN (text gin_trgm_ops);

-- Index for the comments table
CREATE INDEX idx_comments_username_trgm ON comments USING GIN (username gin_trgm_ops);
CREATE INDEX idx_comments_text_trgm ON comments USING GIN (text gin_trgm_ops);